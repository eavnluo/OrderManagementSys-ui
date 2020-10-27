package com.platform.modules.web;

import com.platform.annotation.IgnoreAuth;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.*;
import com.platform.common.validator.AbstractAssert;
import com.platform.modules.base.entity.BaseCustomerInfoEntity;
import com.platform.modules.base.entity.BaseSupplyInfoEntity;
import com.platform.modules.base.service.BaseCustomerInfoService;
import com.platform.modules.base.service.BaseSupplyInfoService;
import com.platform.modules.register.entity.RegisterUserEntity;
import com.platform.modules.register.service.RegisterUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Created by Tp-wangwh on 2019-9-16.
 */
@Slf4j
@RestController
@RequestMapping("/web/user")
@Api(tags = "UserController|注册用户接口")
public class UserController extends ApiBaseController {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private RegisterUserService registerUserService;
    @Autowired
    private JedisUtil jedisUtil;
    @Autowired
    private BaseSupplyInfoService baseSupplyInfoService;
    @Autowired
    private BaseCustomerInfoService baseCustomerInfoService;

    /**
     * 短信验证码 缓存KEY值前缀
     */
    private static final String SMS_PRE = "SMS_";
    /**
     * 注册短信类型
     */
    private static final String MSG_TYPE_01 = "01";
    /**
     * 找回密码短信类型
     */
    private static final String MSG_TYPE_02 = "02";
    /**
     * 缓存时间 10分钟
     */
    private static final int CACHE_SECONDS = 60*10;

    /**
     * 用户名密码登录
     * @param userEntity
     * @return
     */
    @IgnoreAuth
    @PostMapping("login")
    @ApiOperation(value = "手机号密码登录", notes = "根据手机号密码登录")
    public RestResponse login(@RequestBody RegisterUserEntity userEntity) {
        AbstractAssert.isBlank(userEntity.getPhoneNumber(), "账号不能为空");
        AbstractAssert.isBlank(userEntity.getPassword(), "密码不能为空");
        //用户登录
        RegisterUserEntity user = registerUserService.login(userEntity.getPhoneNumber(), userEntity.getPassword());
        //生成token
        String token = jwtUtils.generateToken(user.getId());
        // 默认客户类型
        String userType = "C";
        String companyCode = "";
        String companyName = "";
        if (StringUtils.ONE.equals(user.getType())) {
            try {
                // 如果是供应商
                BaseSupplyInfoEntity supplier = baseSupplyInfoService.findByRegistryUserId(user.getId());
                if (supplier != null) {
                    userType = supplier.getType();
                    companyCode = supplier.getSupplyCode();
                    companyName = supplier.getSupplyName();
                }
            } catch (Exception e) {
                log.error("", e);
            }
        } else {
            // 客户
            try {
                BaseCustomerInfoEntity customer = baseCustomerInfoService.findByRegistryUserId(user.getId());
                if (customer != null) {
                    companyCode = customer.getCustomerCode();
                    companyName = customer.getCustomerName();
                }
            } catch (Exception e) {
                log.error("", e);
            }
        }
        Map<String, Object> map = new HashMap<>(4);
        map.put("token", token);
        map.put("openId", user.getId());
        map.put("user", user);
        map.put("expire", jwtUtils.getExpire());
        map.put("userType", userType);
        map.put("companyCode", companyCode);
        map.put("companyName", companyName);
        return RestResponse.success(map);
    }

    /**
     * 用户注册
     * @param user 注册信息
     * @return 注册结果
     */
    @IgnoreAuth
    @PostMapping("register")
    @ApiOperation(value = "用户注册", notes = "客户和供应商注册账户")
    public RestResponse register(@RequestBody RegisterUserEntity user) {
        // 获取缓存中的注册短信验证码
        String code = jedisUtil.get(SMS_PRE + MSG_TYPE_01 + "_" + user.getPhoneNumber());
        if (StringUtils.isBlank(code) || !code.equals(user.getCode())) {
            throw new BusinessException("请输入正确的短信验证码");
        }
        user.setStatus("0");
        registerUserService.add(user);
        return  RestResponse.success();
    }

    /**
     * 发送短信 获取验证码
     * @param user codeType 短信类型，01：注册短信，02：找回密码短信， phoneNumber 手机号
     * @return
     */
    @IgnoreAuth
    @PostMapping("sendSms")
    @ApiOperation(value = "发送短信验证码", notes = "发送注册短信或找回密码短信")
    public RestResponse sendSms(@RequestBody RegisterUserEntity user) {
        if (StringUtils.isBlank(user.getCodeType()) ) {
            throw new BusinessException("请输入短信类型");
        } else if (!MSG_TYPE_01.equals(user.getCodeType()) && !MSG_TYPE_02.equals(user.getCodeType())) {
            throw new BusinessException("请输入正确的短信类型");
        }
        AbstractAssert.isBlank(user.getPhoneNumber(), "请输入手机号");
        String[] phoneNumberArr = {"+86" + user.getPhoneNumber()};
        // 生成短信验证码
        String code = TencentSmsUtil.createCode();
        String[] templateParams = {"聚潍通OMS", code};
        TencentSmsUtil.SendSms(phoneNumberArr, MSG_TYPE_01.equals(user.getCodeType()) ? TencentSmsUtil.TEMPLATE_ID_01 : TencentSmsUtil.TEMPLATE_ID_02, templateParams);

        // 缓存短信验证码
        jedisUtil.set(SMS_PRE + user.getCodeType() + "_" + user.getPhoneNumber(), code, CACHE_SECONDS);
        return RestResponse.success();
    }


    /**
     * 用户找回密码：phoneNumber、password、code
     * @return
     */
    @IgnoreAuth
    @PostMapping("retrievePassword")
    @ApiOperation(value = "找回密码", notes = "找回密码")
    public RestResponse retrievePassword(@RequestBody RegisterUserEntity user) {
        AbstractAssert.isBlank(user.getPhoneNumber(), "请输入手机号");
        AbstractAssert.isBlank(user.getPassword(), "请输要修改的密码");
        String code = jedisUtil.get(SMS_PRE + MSG_TYPE_02 + "_" + user.getPhoneNumber());
        if (StringUtils.isBlank(code) || !code.equals(user.getCode())) {
            throw new BusinessException("请输入正确的短信验证码");
        }
        registerUserService.editPassword(user.getPhoneNumber(), user.getPassword());
        return RestResponse.success();
    }

    /**
     * 用户修改密码：phoneNumber、password
     * @return
     */
    @PostMapping("editPassword")
    @ApiOperation(value = "找回密码", notes = "找回密码")
    public RestResponse eidtPassword(@RequestBody RegisterUserEntity user) {
        AbstractAssert.isBlank(user.getPhoneNumber(), "请输入手机号");
        AbstractAssert.isBlank(user.getPassword(), "请输要修改的密码");
        registerUserService.editPassword(user.getPhoneNumber(), user.getPassword());
        return RestResponse.success();
    }

}
