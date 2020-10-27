package com.platform.modules.wms.service;

/**
 * SY-WMS接口
 * @author Mr.panbb
 * @date 2019-09-20
 */
public interface SyWmsService {
    /**
     * 将订单下发到仓库
     * @param orderItem 工作单号
     */
    void sendOrder(String orderItem);
}
