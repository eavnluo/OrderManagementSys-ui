/*
 * 项目名称:platform-plus
 * 类名称:WebsiteNewsController.java
 * 包名称:com.platform.modules.website.controller
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-04-10 15:19:02        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.website;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.platform.common.annotation.SysLog;
import com.platform.common.utils.RestResponse;
import com.platform.modules.sys.controller.AbstractController;
import com.platform.modules.sys.entity.SysFileAttachmentEntity;
import com.platform.modules.sys.service.SysFileAttachmentService;
import com.platform.modules.website.entity.WebsiteNewsEntity;
import com.platform.modules.website.service.WebsiteNewsService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author zlm
 * @date 2020-04-10 15:19:02
 */
@RestController
@RequestMapping("website/news")
public class WebsiteNewsController extends AbstractController {
    @Autowired
    private WebsiteNewsService websiteNewsService;
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
    @RequiresPermissions("website:news:list")
    public RestResponse queryAll(@RequestParam Map<String, Object> params) {
        List<WebsiteNewsEntity> list = websiteNewsService.queryAll(params);

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
        Page page = websiteNewsService.queryPage(params);

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
        WebsiteNewsEntity websiteNews = websiteNewsService.getById(id);

        return RestResponse.success().put("news", websiteNews);
    }

    /**
     * 新增
     *
     * @param websiteNews websiteNews
     * @return RestResponse
     */
    @SysLog("新增")
    @RequestMapping("/save")
    public RestResponse save(@RequestBody WebsiteNewsEntity websiteNews) {
        websiteNews.setPublishTime(new Date());
        WebsiteNewsEntity entity = websiteNewsService.add(websiteNews);
        return RestResponse.success().put("id", entity.getId());
    }

    /**
     * 修改
     *
     * @param websiteNews websiteNews
     * @return RestResponse
     */
    @SysLog("修改")
    @RequestMapping("/update")
    public RestResponse update(@RequestBody WebsiteNewsEntity websiteNews) {

        WebsiteNewsEntity entity = websiteNewsService.update(websiteNews);

        return RestResponse.success().put("id", entity.getId());
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
        websiteNewsService.deleteBatch(ids);

        return RestResponse.success();
    }

    @RequestMapping("/upload")
    public RestResponse upload(@RequestParam("file") MultipartFile file, @RequestParam("belongTo") String belongTo) {
        SysFileAttachmentEntity entity = sysFileAttachmentService.webDoUpload(file, belongTo, filePath, null, null);
        List<SysFileAttachmentEntity> list = sysFileAttachmentService.getListByBelongTo(belongTo);
        if (list.size() == 0) {
            sysFileAttachmentService.add(entity);
        } else {
            sysFileAttachmentService.update(entity, new UpdateWrapper<SysFileAttachmentEntity>().eq("BELONG_TO", belongTo));
        }
        return RestResponse.success();

    }
}
