package com.platform.modules.web.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.annotation.LoginUser;
import com.platform.common.OrderType;
import com.platform.common.RegisterUserType;
import com.platform.common.SupplyType;
import com.platform.common.exception.BusinessException;
import com.platform.common.utils.*;
import com.platform.modules.base.entity.BaseCustomerContactsEntity;
import com.platform.modules.base.entity.BaseCustomerInfoEntity;
import com.platform.modules.base.entity.BaseSupplyInfoEntity;
import com.platform.modules.base.service.BaseCustomerContactsService;
import com.platform.modules.base.service.BaseCustomerInfoService;
import com.platform.modules.base.service.BaseSupplyInfoService;
import com.platform.modules.order.entity.*;
import com.platform.modules.order.service.*;
import com.platform.modules.register.entity.RegisterUserEntity;
import com.platform.modules.register.service.RegisterUserService;
import com.platform.modules.sys.entity.SysFileAttachmentEntity;
import com.platform.modules.sys.service.SysFileAttachmentService;
import com.platform.modules.web.ApiBaseController;
import com.platform.modules.wms.entity.StoreInventoryGoodsEntity;
import com.platform.modules.wms.entity.StoreInventoryInfoEntity;
import com.platform.modules.wms.service.StoreInventoryGoodsService;
import com.platform.modules.wms.service.StoreInventoryInfoService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * web订单接口
 *
 * @author 朱黎明
 * @date 2020-03-23
 */
@Slf4j
@RestController
@RequestMapping("/web/order")
@Api(tags = "OrderHeaderInfoController|web订单接口")
public class OrderHeaderInfoController extends ApiBaseController {

    @Autowired
    private OrderHeaderInfoService orderHeaderInfoService;
    @Autowired
    private RegisterUserService registerUserService;
    @Autowired
    private OrderDetailGoodsInfoService orderDetailGoodsInfoService;
    @Autowired
    private OrderCarRelationService orderCarRelationService;
    @Autowired
    private BaseSupplyInfoService baseSupplyInfoService;
    @Autowired
    private SysFileAttachmentService sysFileAttachmentService;
    @Autowired
    private StoreInventoryGoodsService storeInventoryGoodsService;
    @Autowired
    private BaseCustomerInfoService baseCustomerInfoService;
    @Autowired
    private StoreInventoryInfoService storeInventoryInfoService;
    @Autowired
    private OrderStatusFlowService orderStatusFlowService;
    @Autowired
    private BaseCustomerContactsService baseCustomerContactsService;
    @Autowired
    private OrderTrackInfoService orderTrackInfoService;

    @Value("${upload.file-path}")
    private String filePath;

    /**
     * 用户下单
     *
     * @param user      登录用户
     * @param orderInfo 订单信息
     * @return
     */
    @PostMapping("/saveCompleteOrder")
    public RestResponse saveCompleteOrder(@LoginUser RegisterUserEntity user, @RequestBody @Valid OrderHeaderInfoEntity orderInfo) {
        // 判断当前登录用户类型是否为客户
        if (!RegisterUserType.CUSTOMER.equals(user.getType())) {
            throw new BusinessException("当前登录的用户不是客户，不允许下单");
        }
        // 判断客户是否认证
        if (!registerUserService.isAuthentication(user)) {
            throw new BusinessException("企业未认证，请先完善信息");
        }
        // 绑定客户ID
        orderInfo.setCustomerId(user.getId());
        // 下单
        orderHeaderInfoService.addCompleteOrder(orderInfo);
        return RestResponse.success();
    }

    /**
     * 分页查询订单列表
     *
     * @param user   当前登录用户
     * @param params 查询条件
     * @return
     */
    @GetMapping("/list")
    public RestResponse list(@LoginUser RegisterUserEntity user, @RequestParam Map<String, Object> params) {
        // 展示对应用户的订单列表
        Page page = orderHeaderInfoService.queryPage(params, user);
        return RestResponse.success().put("page", page);
    }

    /**
     * 获取订单内容
     *
     * @param orderItem 工作单号
     * @return
     */
    @GetMapping("/info/{orderItem}")
    public RestResponse info(@PathVariable("orderItem") String orderItem) {
        JSONObject data = new JSONObject();
        // 获取订单头信息
        OrderHeaderInfoEntity orderHeaderInfo = orderHeaderInfoService.getByOrderItem(orderItem);
        // 获取订单商品信息列表
        if (orderHeaderInfo.getStorageSupplyStatus().equals(Constant.STR_ONE)) {
            // 已维护仓储信息
            List<StoreInventoryGoodsEntity> goodsList = storeInventoryGoodsService.listByOrderItem(orderItem);
            orderHeaderInfo.setOutInOddNum(goodsList.get(0).getOutInOddNum());
            data.put("goodsList", goodsList);
        } else {
            // 没有维护仓储信息
            List<OrderDetailGoodsInfoEntity> goodsList = orderDetailGoodsInfoService.listByOrderItem(orderItem);
            data.put("goodsList", goodsList);
        }

        // 获取订单车辆信息
        List<OrderCarRelationEntity> carList = orderCarRelationService.listByOrderItem(orderItem);
        // 获取收货人和发货人信息
        List<BaseCustomerContactsEntity> contactsList = baseCustomerContactsService.getOrderContactsList(orderHeaderInfo);
        // 获取物流宝信息
        OrderTrackInfoEntity trackInfo= orderTrackInfoService.getByOrderItem(orderItem);

        data.put("orderHeaderInfo", orderHeaderInfo);
        data.put("carList", carList);
        data.put("contactsList", contactsList);
        data.put("trackInfo", trackInfo);
        return RestResponse.success().put("data", data);
    }

    /**
     * 导出订单信息
     *
     * @param params 查询参数
     * @param response 请求响应对象
     */
    @GetMapping("/exportData")
    public void exportData(@RequestParam Map<String, Object> params, HttpServletResponse response) {
        OrderExportUtil.orderExport(orderHeaderInfoService, params, response, "static/export_order.xls");
    }

    /**
     * 完善订单信息
     *
     * @param user   当前登录用户
     * @param params 要完善的信息参数
     * @return
     */
    @PostMapping("/perfectOrderInfo")
    public RestResponse perfectOrderInfo(@LoginUser RegisterUserEntity user, @RequestBody JSONObject params) {
        if (user.getType().equals(RegisterUserType.CUSTOMER)) {
            // 登录的是客户
            throw new BusinessException("当前登录用户不是供应商，此操作不被允许");
        }
        // 获取供应商信息
        BaseSupplyInfoEntity supplyInfo = baseSupplyInfoService.findByRegistryUserId(user.getId());
        // 获取订单信息
        OrderHeaderInfoEntity orderInfo = orderHeaderInfoService.getOne(new QueryWrapper<OrderHeaderInfoEntity>().eq("ORDER_ITEM", params.getString("orderItem")));
        // 获取供应商类型数组
        String[] typeArray = parseSupplyType(supplyInfo.getType(), orderInfo, supplyInfo);
        for (String s : typeArray) {
            if (s.equals(SupplyType.K_) && supplyInfo.getId().equals(orderInfo.getCarSupplyId())) {
                // 卡车供应商，完善订单卡车信息
                orderCarRelationService.perfectOrderCarInfo(params, orderInfo);
            } else if (s.equals(SupplyType.F_) && supplyInfo.getId().equals(orderInfo.getStorageSupplyId())) {
                // 仓储供应商，完善订单仓储信息
                orderDetailGoodsInfoService.perfectOrderGoodsInfo(params, orderInfo, supplyInfo);
            }
        }

        return RestResponse.success();
    }

    /**
     * 上传附件
     *
     * @param file     上传的文件
     * @param belongTo 工作单号
     */
    @PostMapping("/uploadFile")
    public RestResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("belongTo") String belongTo) {
        sysFileAttachmentService.upload(file, belongTo, filePath);
        return RestResponse.success();
    }

    /**
     * 根据belongTo获取文件信息列表
     *
     * @param belongTo belongTo
     * @return
     */
    @GetMapping("/getFilesByBelongTo")
    public RestResponse getFilesByBelongTo(@RequestParam("belongTo") String belongTo) {
        List<SysFileAttachmentEntity> list = sysFileAttachmentService.getListByBelongTo(belongTo);
        return RestResponse.success().put("files", list);
    }

    /**
     * 下载附件
     *
     * @param id 下载文件id
     */
    @GetMapping("/downloadFile")
    public void downloadFile(@RequestParam("id") String id) {
        SysFileAttachmentEntity file = sysFileAttachmentService.getById(id);
        if (file != null) {
            FileDownloadUtils.download(file.getFileUrl(), file.getFileName(), response);
        }
    }

    /**
     * 获取当前登录用户近12个月的订单数量
     *
     * @return
     */
    @GetMapping("/statisticsOrderNum")
    public RestResponse statisticsOrderNum(@LoginUser RegisterUserEntity user) {
        JSONObject data = new JSONObject();
        Map<String, Object> params = new HashMap<>();
        // 判断当前登录用户是客户还是供应商
        if (user.getType().equals(RegisterUserType.CUSTOMER)) {
            // 客户
            params.put("customerId", user.getId());
        } else {
            // 供应商
            BaseSupplyInfoEntity supplyInfo;
            try {
                supplyInfo = baseSupplyInfoService.findByRegistryUserId(user.getId());
            } catch (Exception e) {
                data.put("inOrder", new JSONArray());
                data.put("outOrder", new JSONArray());
                return RestResponse.success().put("data", data);
            }
            params.put("supplyId", supplyInfo.getId());
        }
        // 统计采购订单
        List<Map<String, Object>> inOrder = orderHeaderInfoService.getOrderNumByType(OrderType.R_, params);
        // 统计销售订单
        List<Map<String, Object>> outOrder = orderHeaderInfoService.getOrderNumByType(OrderType.I_, params);
        data.put("inOrder", inOrder);
        data.put("outOrder", outOrder);

        return RestResponse.success().put("data", data);
    }

    /**
     * 统计当前登录用户库存信息
     *
     * @param user
     * @return
     */
    @RequestMapping("/statisticsStore")
    public RestResponse statisticsStore(@LoginUser RegisterUserEntity user) {
        Map<String, Object> params = new HashMap<>();
        // 判断当前登录用户类型
        if (user.getType().equals(RegisterUserType.CUSTOMER)) {
            // 客户
            BaseCustomerInfoEntity customerInfo = baseCustomerInfoService.findByRegistryUserId(user.getId());
            if (customerInfo == null) {
                return RestResponse.success().put("data", null);
            }
            params.put("ownerCode", customerInfo.getCustomerCode());
        } else {
            // 供应商
            BaseSupplyInfoEntity supplyInfo;
            try {
                supplyInfo = baseSupplyInfoService.findByRegistryUserId(user.getId());
            } catch (Exception e) {
                return RestResponse.success().put("data", null);
            }
            params.put("storeCode", supplyInfo.getSupplyCode());

        }
        List<StoreInventoryInfoEntity> list = storeInventoryInfoService.statisticsStore(params);
        // TODO 商品数组
        // 仓库数组
        // 商品数量数组

        return RestResponse.success();
    }

    /**
     * 获取订单状态列表
     *
     * @param params
     * @return
     */
    @RequestMapping("/getOrderStatusFlow")
    public RestResponse getOrderStatusFlow(@RequestParam Map<String, Object> params) {
        List<OrderStatusFlowEntity> list = orderStatusFlowService.queryAll(params);
        return RestResponse.success().put("list", list);
    }

    private String[] parseSupplyType(String type, OrderHeaderInfoEntity order, BaseSupplyInfoEntity supply) {
        String supplyId = supply.getId();
        String carSupplyId = order.getCarSupplyId();
        String storageSupplyId = order.getStorageSupplyId();
        // 订单的卡车供应商和仓储供应商是同一个人
        if (supplyId.equals(carSupplyId) && supplyId.equals(storageSupplyId)) {
            if (order.getOrderType().equals(OrderType.R_)) {
                // 采购订单，先维护卡车信息，再维护仓储信息
                return new String[]{SupplyType.K_, SupplyType.F_};
            } else {
                // 销售订单，先维护仓储信息，再维护卡车信息
                return new String[]{SupplyType.F_, SupplyType.K_};
            }
        } else {
            // 订单的卡车供应商和仓储供应商不是同一个人
            return type.split(",");
        }
    }

    /**
     * 保存追踪宝与订单绑定
     *
     * @param trackInfo
     * @return
     */
    @RequestMapping("/stackInfo/add")
    public RestResponse saveStackInfo(@RequestBody OrderTrackInfoEntity trackInfo) {
        log.error("[receiveStatusForReceipt]: " + JSON.toJSONString(trackInfo));
        try {
            //验证该订单是否存在
            if (orderHeaderInfoService.getByOrderItem(trackInfo.getOrderItem()) == null) {
                return RestResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "订单" + trackInfo.getOrderItem() + "不存在");
            }
            if (StringUtils.isEmpty(trackInfo.getDeviceCode())) {
                return RestResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "绑定设备的编号不能为空");
            }
            OrderTrackInfoEntity info = orderTrackInfoService.getByOrderItem(trackInfo.getOrderItem());
            if (info == null) {
                if (trackInfo.getStartTime() == null) {
                    return RestResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "初次绑定设备时开始时间不能为空");
                }
                trackInfo.setCreatedTime(new Date());
                // 插入新的记录
                orderTrackInfoService.add(trackInfo);
            } else {
                if (trackInfo.getStartTime() == null || trackInfo.getEndTime() == null) {
                    return RestResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "再次绑定设备时开始时间或结束时间不能为空");
                }
                // 更新记录
                info.setDeviceCode(trackInfo.getDeviceCode());
                info.setStartTime(trackInfo.getStartTime());
                info.setEndTime(trackInfo.getEndTime());
                info.setUpdatedTime(new Date());
                orderTrackInfoService.update(info);
            }
            return RestResponse.success("操作成功");
        } catch (Exception e) {
            log.error("", e);
        }
        return RestResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "操作失败");
    }

}
