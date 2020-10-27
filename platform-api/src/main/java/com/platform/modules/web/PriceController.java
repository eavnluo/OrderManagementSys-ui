package com.platform.modules.web;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.annotation.LoginUser;
import com.platform.common.RegisterUserType;
import com.platform.common.utils.Constant;
import com.platform.common.utils.RestResponse;
import com.platform.modules.base.entity.BaseCustomerInfoEntity;
import com.platform.modules.base.entity.BaseSupplyInfoEntity;
import com.platform.modules.base.service.BaseCustomerInfoService;
import com.platform.modules.base.service.BaseSupplyInfoService;
import com.platform.modules.price.entity.PriceDetailEntity;
import com.platform.modules.price.entity.PriceInfoEntity;
import com.platform.modules.price.entity.PriceItemEntity;
import com.platform.modules.price.service.PriceDetailService;
import com.platform.modules.price.service.PriceInfoService;
import com.platform.modules.price.service.PriceItemService;
import com.platform.modules.register.entity.RegisterUserEntity;
import com.platform.modules.sys.entity.SysFileAttachmentEntity;
import com.platform.modules.sys.service.SysFileAttachmentService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报价接口
 *
 * @author Mr.panbb
 * @date 2020=0327
 */
@Slf4j
@RestController
@RequestMapping("/web/price")
@Api(tags = "PriceController|报价接口")
public class PriceController extends ApiBaseController {

    @Autowired
    private PriceInfoService priceInfoService;
    @Autowired
    private PriceDetailService priceDetailService;
    @Autowired
    private PriceItemService priceItemService;
    @Autowired
    private SysFileAttachmentService sysFileAttachmentService;
    @Autowired
    private BaseSupplyInfoService baseSupplyInfoService;
    @Autowired
    private BaseCustomerInfoService baseCustomerInfoService;

    /**
     * 分页查询报价列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    public RestResponse list(@LoginUser RegisterUserEntity user, @RequestParam Map<String, Object> params) {
        try {
            // 判断登录是用户还是供应商
            if (user.getType().equals(RegisterUserType.CUSTOMER)) {
                // 客户
                // 获取客户代码，查询给客户的报价
                BaseCustomerInfoEntity customerInfo = baseCustomerInfoService.findByRegistryUserId(user.getId());
                if (customerInfo != null) {
                    params.put("companyCode",customerInfo.getCustomerCode());
                } else {
                    return RestResponse.success();
                }
                params.put("type", Constant.STR_ZERO);
            } else {
                // 供应商
                // 获取供应商代码，查询当前供应商的报价
                BaseSupplyInfoEntity supplyInfo = baseSupplyInfoService.findByRegistryUserId(user.getId());
                if (supplyInfo != null) {
                    params.put("companyCode",supplyInfo.getSupplyCode());
                } else {
                    return RestResponse.success();
                }
                params.put("type", Constant.STR_ONE);
            }
            Page page = priceInfoService.queryPage(params);
            return RestResponse.success().put("page", page);
        } catch (Exception e) {
            log.error("", e);
        }
        return RestResponse.success();
    }

    /**
     * 根据报价信息
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info")
    public RestResponse info(@RequestParam("id") String id) {
        // 报价单
        PriceInfoEntity priceInfo = priceInfoService.getById(id);
        // 报价明细
        Map<String, Object> map = new HashMap<>(2);
        map.put("priceInfoId", id);
        List<PriceDetailEntity> priceDetailList = priceDetailService.queryAll(map);
        priceInfo.setPriceDetailList(priceDetailList);
        // 附件列表
        map.put("belongTo", id);
        List<SysFileAttachmentEntity> fileList = sysFileAttachmentService.queryAll(map);
        priceInfo.setFileList(fileList);
        return RestResponse.success().put("info", priceInfo);
    }

    /**
     * 创建报价单编号
     *
     * @return RestResponse
     */
    @RequestMapping("/createPriceNo")
    public RestResponse createPriceNo() {
        String priceNo = priceInfoService.createPriceNo();
        return RestResponse.success().put("priceNo", priceNo);
    }

    /**
     * 查询报价费用列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/findPriceItemList")
    public RestResponse findPriceItemList(@RequestParam Map<String, Object> params) {
        List<PriceItemEntity> list = priceItemService.queryAll(params);

        return RestResponse.success().put("list", list);
    }


    /**
     * 保存报价信息
     *
     * @param priceInfo priceInfo
     * @return RestResponse
     */
    @RequestMapping("/save")
    public RestResponse save(@LoginUser RegisterUserEntity user, @RequestBody PriceInfoEntity priceInfo) {
        priceInfo.setCreateBy(user.getId());
        priceInfo.setCreateTime(new Date());
        priceInfoService.add(priceInfo);
        return RestResponse.success().put("id", priceInfo.getId());
    }
}
