package com.platform.modules.iot;

import com.platform.modules.order.entity.OrderHeaderInfoEntity;

/**
 * 物联网平台接口
 *
 * @author Mr.panbb
 * @date 2019-09-18
 */
public interface IotService {
	/**
	 * 下发运输入库运输信息
	 *
	 * @param wayBillNo 运输单号
	 * @return true 下发成功，false 下发失败
	 */
	boolean sentInInfo(String wayBillNo);

	/**
	 * 下发出库运输信息
	 *
	 * @param wayBillNo 运输单号
	 * @return true 下发成功，false 下发失败
	 */
	boolean sendOutInfo(String wayBillNo);

	/**
	 * 下发组织基本信息
	 *
	 * @return
	 */
	public boolean sendOrgInfo();

	/**
	 * 下发工厂基本信息
	 *
	 * @return
	 */
	public boolean sendStoreInfo();

	/**
	 * 下发产品基本信息
	 *
	 * @return
	 */
	boolean sendGoodsInfo();

	/**
	 * 下发用户基本信息
	 *
	 * @return
	 */
	boolean sendUserInfo();

	/**
	 * 出入库单同步
	 *
	 * @param order
	 */
	boolean orderSyn(OrderHeaderInfoEntity order);
}
