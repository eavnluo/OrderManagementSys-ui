package com.platform.modules.order;

import com.platform.common.utils.RestResponse;
import com.platform.modules.web.ApiBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Created by Tp-wangwh on 2019-9-3.
 */
@Api(tags = "OrderRecevieController|接单接口")
@RestController
@RequestMapping("/app/order/")
public class OrderRecevieController extends ApiBaseController {

    @ApiOperation(value = "接单接口", notes = "上传文件，form表单提交")
    @RequestMapping(value = "recevie")
    public RestResponse  recevieOrderData(HttpServletRequest request){
        try {
            //TODO.接单接口的逻辑的处理结果
            return  RestResponse.success();
        }catch (Exception e){
            return  RestResponse.error(e.toString());
        }
    }

}
