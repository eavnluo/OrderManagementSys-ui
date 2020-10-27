package com.platform.modules.web.base;

import com.platform.annotation.LoginUser;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.modules.base.entity.BaseCustomerInfoEntity;
import com.platform.modules.base.entity.BaseSupplyInfoEntity;
import com.platform.modules.base.service.BaseCustomerInfoService;
import com.platform.modules.base.service.BaseSupplyInfoService;
import com.platform.modules.register.entity.RegisterUserEntity;
import com.platform.modules.web.ApiBaseController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * web供应商接口
 *
 * @author 朱黎明
 * @date 2020-03-23
 */
@Slf4j
@RestController
@RequestMapping("/web/supply")
@Api(tags = "BaseSupplyInfoController|web供应商接口")
public class BaseSupplyInfoController extends ApiBaseController {
    @Autowired
    private BaseSupplyInfoService baseSupplyInfoService;
    @Autowired
    private BaseCustomerInfoService baseCustomerInfoService;

    /**
     * 根据供应商类型获取供应商列表
     *
     * @param type 供应商类型
     * @return
     */
    @GetMapping("/findListByType")
    public RestResponse findListByType(@LoginUser RegisterUserEntity user, @RequestParam("type") String type) {
        if (StringUtils.isBlank(type.trim())) {
            throw new BusinessException("供应商类型不允许为空");
        }
        // 获取客户信息
        BaseCustomerInfoEntity customerInfo = baseCustomerInfoService.findByRegistryUserId(user.getId());
        if (customerInfo == null) {
            return RestResponse.success().put("list", new ArrayList<>());
        }
        // 获取已报价的供应商列表
        List<BaseSupplyInfoEntity> list = baseSupplyInfoService.findListByType(customerInfo.getCustomerCode(), type);
        return RestResponse.success().put("list", list);
    }
}
