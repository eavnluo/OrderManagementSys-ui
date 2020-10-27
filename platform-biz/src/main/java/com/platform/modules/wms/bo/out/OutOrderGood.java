package com.platform.modules.wms.bo.out;

import com.platform.modules.wms.bo.OrderGood;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author Created by Tp-wangwh on 2019-9-3.
 */
@ApiModel(value = "出库的订单的商品信息")
@Data
public class OutOrderGood extends OrderGood {
    /**
     *
     */
    int outboundPackCount;
    /**
     *
     */
    int outboundQty;
}
