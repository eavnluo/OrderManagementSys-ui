package com.platform.modules.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Maps;
import com.platform.annotation.LoginUser;
import com.platform.common.utils.RestResponse;
import com.platform.common.utils.StringUtils;
import com.platform.modules.web.ApiBaseController;
import com.platform.modules.order.entity.OrderGoodUniqueCodeScanEntity;
import com.platform.modules.order.entity.OrderGoodUniqueCodeStandardEntity;
import com.platform.modules.order.service.OrderGoodUniqueCodeScanService;
import com.platform.modules.order.service.OrderGoodUniqueCodeStandardService;
import com.platform.modules.sys.entity.SysMobileUserEntity;
import com.platform.modules.wms.service.InPlanLogisticService;
import com.platform.modules.wms.service.OutPlanLogisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/app/order")
public class OrderGoodUniqueCodeController extends ApiBaseController {

    @Autowired
    private OrderGoodUniqueCodeStandardService orderGoodUniqueCodeStandardService;

    @Autowired
    private OrderGoodUniqueCodeScanService orderGoodUniqueCodeScanService;

    @Autowired
    private InPlanLogisticService inPlanLogisticService;
    @Autowired
    private OutPlanLogisticService outPlanLogisticService;

    /***
     * 入库订单信息
     * @param params
     * @param sysMobileUserEntity
     * @return
     */
    @RequestMapping(value ="inPlanByMobile" )
    public RestResponse inPlanByMobile(@RequestParam Map<String, String> params, @LoginUser SysMobileUserEntity sysMobileUserEntity){
        Page  page=inPlanLogisticService.queryPageByTelePhone(params,sysMobileUserEntity.getMobile());
        Map<String,Object> map= Maps.newHashMap();
        map.put("page",page);
        return RestResponse.success(map);
    }
    /***
     * 出库订单信息
     * @param params
     * @param sysMobileUserEntity
     * @return
     */
    @RequestMapping(value ="outPlanByMobile" )
    public RestResponse outPlanByMobile(@RequestParam Map<String, String> params, @LoginUser SysMobileUserEntity sysMobileUserEntity){
        Page  page=outPlanLogisticService.queryPageByTelePhone(params,sysMobileUserEntity.getMobile());
        Map<String,Object> map= Maps.newHashMap();
        map.put("page",page);
        return RestResponse.success(map);
    }





    @RequestMapping(value = "/scan", method = RequestMethod.POST)
    public RestResponse recevieOrderData(OrderGoodUniqueCodeScanEntity paramEntity,@LoginUser SysMobileUserEntity sysMobileUserEntity) {
        String orderItem = paramEntity.getOrderItem();
        String uniqueCode = paramEntity.getUniqueCode();
        String operateType = paramEntity.getOperateType();
        //参数校验
        if (StringUtils.isBlank(orderItem) || StringUtils.isBlank(uniqueCode) || StringUtils.isBlank(operateType)){
            return RestResponse.error(-10, "参数错误");
        }

        //校验扫描表是否存在相同记录（操作结果为success且内部号，唯一编码，操作类型都相同视为同一记录）
        boolean isExist = orderGoodUniqueCodeScanService.isExist(orderItem, uniqueCode, operateType);
        if (isExist){
            return RestResponse.error(-30, "重复操作");
        }

        //查找标准商品标识码
        List<OrderGoodUniqueCodeStandardEntity> standardCodes = orderGoodUniqueCodeStandardService.findByOrderItem(orderItem);
        if (standardCodes.size() == 0){
            return RestResponse.error(-20, "当前订单没有对应出库商品");
        }

        OrderGoodUniqueCodeStandardEntity standardEntity = null;
        for (OrderGoodUniqueCodeStandardEntity item : standardCodes) {
            if (uniqueCode.equals(item.getUniqueCode())) {
                standardEntity = item;
            }
        }


        //将标准码实体转为扫描表实体
        OrderGoodUniqueCodeScanEntity scanEntity = new OrderGoodUniqueCodeScanEntity();
        scanEntity.setUniqueCode(uniqueCode);
        scanEntity.setOrderItem(orderItem);
        scanEntity.setOperateType(operateType);
        //todo 获取当前用户
        scanEntity.setOperatorName(sysMobileUserEntity.getName());
        scanEntity.setOperatorCode(sysMobileUserEntity.getMobile());
        scanEntity.setOperatorDate(new Date());
        if (standardEntity != null) {
            standardEntity.addGoodInfo(scanEntity);
            scanEntity.setOperateResult(OrderGoodUniqueCodeScanEntity.OPERATE_RESULT_SUCCESS);
            orderGoodUniqueCodeScanService.add(scanEntity);
            return RestResponse.success("success");

        } else {
            scanEntity.setOperateResult(OrderGoodUniqueCodeScanEntity.OPERATE_RESULT_FAILURE);
            orderGoodUniqueCodeScanService.add(scanEntity);
            return RestResponse.error(-1, "failure");
        }


    }

}
