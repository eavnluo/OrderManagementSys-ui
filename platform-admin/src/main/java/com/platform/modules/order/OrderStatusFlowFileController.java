/*
 * 项目名称:platform-plus
 * 类名称:OrderStatusFlowFileController.java
 * 包名称:com.platform.modules.order.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-07 11:23:38        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.FileDownloadUtils;
import com.platform.common.utils.RestResponse;
import com.platform.modules.order.entity.OrderStatusFlowFileEntity;
import com.platform.modules.order.service.OrderStatusFlowFileService;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.sys.entity.SysFileAttachmentEntity;
import com.platform.modules.sys.service.SysFileAttachmentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author zlm
 * @date 2020-03-07 11:23:38
 */
@RestController
@RequestMapping("order/statusflowfile")
@CrossOrigin(allowCredentials = "true", origins = {"*"})
public class OrderStatusFlowFileController extends AbstractController {
	@Autowired
	private OrderStatusFlowFileService orderStatusFlowFileService;
	@Autowired
	private SysFileAttachmentService sysFileAttachmentService;

	@Value("${upload.file-path}")
	private String filePath;

	/**
	 * 查看所有列表
	 *
	 * @param params 查询参数
	 * @return RestResponse
	 */
	@RequestMapping("/queryAll")
	@RequiresPermissions("order:statusflowfile:list")
	public RestResponse queryAll(@RequestParam Map<String, Object> params) {
		List<OrderStatusFlowFileEntity> list = orderStatusFlowFileService.queryAll(params);

		return RestResponse.success().put("list", list);
	}

	/**
	 * 分页查询
	 *
	 * @param params 查询参数
	 * @return RestResponse
	 */
	@GetMapping("/list")
	@RequiresPermissions("order:statusflowfile:list")
	public RestResponse list(@RequestParam Map<String, Object> params) {
		Page page = orderStatusFlowFileService.queryPage(params);

		return RestResponse.success().put("page", page);
	}

	/**
	 * 根据主键查询详情
	 *
	 * @param id 主键
	 * @return RestResponse
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("order:statusflowfile:info")
	public RestResponse info(@PathVariable("id") String id) {
		OrderStatusFlowFileEntity orderStatusFlowFile = orderStatusFlowFileService.getById(id);

		return RestResponse.success().put("statusflowfile", orderStatusFlowFile);
	}

	/**
	 * 新增
	 *
	 * @param orderStatusFlowFile orderStatusFlowFile
	 * @return RestResponse
	 */
	@SysLog("新增")
	@RequestMapping("/save")
	@RequiresPermissions("order:statusflowfile:save")
	public RestResponse save(@RequestBody OrderStatusFlowFileEntity orderStatusFlowFile) {

		orderStatusFlowFileService.add(orderStatusFlowFile);

		return RestResponse.success();
	}

	/**
	 * 修改
	 *
	 * @param orderStatusFlowFile orderStatusFlowFile
	 * @return RestResponse
	 */
	@SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("order:statusflowfile:update")
	public RestResponse update(@RequestBody OrderStatusFlowFileEntity orderStatusFlowFile) {

		orderStatusFlowFileService.update(orderStatusFlowFile);

		return RestResponse.success();
	}

	/**
	 * 根据主键删除
	 *
	 * @param ids ids
	 * @return RestResponse
	 */
	@SysLog("删除")
	@RequestMapping("/delete")
	@RequiresPermissions("order:statusflowfile:delete")
	public RestResponse delete(@RequestBody String[] ids) {
		orderStatusFlowFileService.deleteBatch(ids);

		return RestResponse.success();
	}

	/**
	 * 上传文件
	 *
	 * @param file 上传的文件
	 * @param orderStatusFlowId 要绑定的订单状态ID
	 * @return RestResponse
	 */
	@RequestMapping("/uploadFile")
	public RestResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("orderStatusFlowId") String orderStatusFlowId) {
		try {
			sysFileAttachmentService.upload(file,orderStatusFlowId,filePath);
			return RestResponse.success();
		} catch (Exception e) {
			logger.error("[上传文件失败]:", e);
			return RestResponse.error("上传文件失败，请稍后再试");
		}
	}

	/**
	 * 下载文件
	 *
	 * @param id 文件路径
	 * @param response
	 */
	@GetMapping("downloadFile")
	public void downLoad(@RequestParam(name = "id") String id, HttpServletResponse response) {
		SysFileAttachmentEntity file = sysFileAttachmentService.getById(id);
		if (file != null) {
			FileDownloadUtils.download(file.getFileUrl(), file.getFileName(), response);
		}
	}

}
