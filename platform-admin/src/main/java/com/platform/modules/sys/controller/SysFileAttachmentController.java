/*
 * 项目名称:platform-plus
 * 类名称:SysFileAttachmentController.java
 * 包名称:com.platform.modules.sys.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-25 14:44:58        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.sys.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.FileDownloadUtils;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.entity.SysFileAttachmentEntity;
import com.platform.modules.sys.service.SysFileAttachmentService;
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
 * @date 2020-03-25 14:44:58
 */
@RestController
@RequestMapping("sys/fileattachment")
public class SysFileAttachmentController extends AbstractController {
    @Autowired
    private SysFileAttachmentService sysFileAttachmentService;

    /**
     * 文件上传默认路径
     */
    @Value("${upload.file-path}")
    private String filePath;

    /**
     * 查看所有列表
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @RequestMapping("/queryAll")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<SysFileAttachmentEntity> list = sysFileAttachmentService.queryAll(params);

        return RestResponse.success().put("list", list);
    }

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return RestResponse
     */
    @GetMapping("/list")
    public RestResponse list(@RequestParam Map<String, Object> params) {
        Page page = sysFileAttachmentService.queryPage(params);

        return RestResponse.success().put("page", page);
    }

    /**
     * 根据主键查询详情
     *
     * @param id 主键
     * @return RestResponse
     */
    @RequestMapping("/info/{id}")
    public RestResponse info(@PathVariable("id") String id) {
        SysFileAttachmentEntity sysFileAttachment = sysFileAttachmentService.getById(id);

        return RestResponse.success().put("fileattachment", sysFileAttachment);
    }

    /**
     * 上传附件
     *
     * @param file     附件
     * @param belongTo 文件归属
     * @return RestResponse
     */
    @SysLog("上传附件")
    @RequestMapping("/upload")
    public RestResponse upload(@RequestBody MultipartFile file, @RequestParam("belongTo") String belongTo) {
        sysFileAttachmentService.upload(file, belongTo, filePath);
        return RestResponse.success();
    }

    /**
     * 上传网站首页图片
     *
     * @param file        附件
     * @param belongTo    类别
     * @param describe    图片描述描述
     * @param linkAddress 链接地址
     * @return RestResponse
     */
    @SysLog("上传网站首页图片")
    @RequestMapping("/webUpload")
    public RestResponse webUpload(@RequestBody MultipartFile file,
                                  @RequestParam("belongTo") String belongTo,
                                  @RequestParam(value = "describe", required = false) String describe,
                                  @RequestParam(value = "linkAddress", required = false) String linkAddress) {
        sysFileAttachmentService.webUpload(file, belongTo, filePath, describe, linkAddress);
        return RestResponse.success();
    }

    /**
     * 修改
     *
     * @param sysFileAttachment sysFileAttachment
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    public RestResponse update(@RequestBody SysFileAttachmentEntity sysFileAttachment) {

        sysFileAttachmentService.update(sysFileAttachment);

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
    public RestResponse delete(@RequestBody String[] ids) {
        sysFileAttachmentService.deleteBatch(ids);

        return RestResponse.success();
    }


    /**
     * 下载文件
     *
     * @param id       文件路径
     * @param response
     */
    @GetMapping("downloadFile")
    public void downloadFile(@RequestParam(name = "id") String id, HttpServletResponse response) {
        SysFileAttachmentEntity file = sysFileAttachmentService.getById(id);
        if (file != null) {
            FileDownloadUtils.download(file.getFileUrl(), file.getFileName(), response);
        }
    }
}
