/*
 * 项目名称:platform-plus
 * 类名称:OrderCarRelationController.java
 * 包名称:com.platform.modules.order.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-24 13:04:44        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.web.order;

import com.platform.modules.register.service.RegisterUserService;
import com.platform.modules.sys.service.SysFileAttachmentService;
import com.platform.modules.web.ApiBaseController;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * web订单车辆信息接口
 *
 * @author 朱黎明
 * @date 2020-03-23
 */
@Slf4j
@RestController
@RequestMapping("/web/ordercar")
@Api(tags = "OrderCarRelationController|web订单车辆信息接口")
public class OrderCarRelationController extends ApiBaseController {
	@Autowired
	private RegisterUserService registerUserService;
	@Autowired
	private SysFileAttachmentService sysFileAttachmentService;

	@Value("${upload.file-path}")
	private String filePath;

}
