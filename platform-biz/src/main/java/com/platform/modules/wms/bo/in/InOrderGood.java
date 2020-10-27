package com.platform.modules.wms.bo.in;

import com.platform.modules.wms.bo.OrderGood;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author Created by Tp-wangwh on 2019-9-3.
 */
@ApiModel("入库流水的信息表")
@Data
public class InOrderGood  extends OrderGood{
    /**
     *
     */
    int inboundPackCount;
    /**
     *
     */
    int inboundQty;
}
