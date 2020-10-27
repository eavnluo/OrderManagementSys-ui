/*
 * 项目名称:platform-plus
 * 类名称:SysFileAttachmentService.java
 * 包名称:com.platform.modules.sys.service
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-25 14:44:58        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.platform.modules.sys.entity.SysFileAttachmentEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Service接口
 *
 * @author zlm
 * @date 2020-03-25 14:44:58
 */
public interface SysFileAttachmentService extends IService<SysFileAttachmentEntity> {

    /**
     * 查询所有列表
     *
     * @param params 查询参数
     * @return List
     */
    List<SysFileAttachmentEntity> queryAll(Map<String, Object> params);

    /**
     * 分页查询
     *
     * @param params 查询参数
     * @return Page
     */
    Page queryPage(Map<String, Object> params);

    /**
     * 新增
     *
     * @param sysFileAttachment
     * @return 新增结果
     */
    boolean add(SysFileAttachmentEntity sysFileAttachment);

    /**
     * 根据主键更新
     *
     * @param sysFileAttachment
     * @return 更新结果
     */
    boolean update(SysFileAttachmentEntity sysFileAttachment);

    /**
     * 根据主键删除
     *
     * @param id id
     * @return 删除结果
     */
    boolean delete(String id);

    /**
     * 根据主键批量删除
     *
     * @param ids ids
     * @return 删除结果
     */
    boolean deleteBatch(String[] ids);

    /**
     * 上传附件
     *
     * @param file     文件
     * @param belongTo 文件归属
     * @param filePath 上传路径
     */
    void upload(MultipartFile file, String belongTo, String filePath);

    /**
     * 根据属于查询附件列表
     *
     * @param belongTo
     * @return
     */
    List<SysFileAttachmentEntity> getListByBelongTo(String belongTo);

    /**
     * 网站维护图片
     *
     * @param file        图片文件
     * @param belongTo    类别
     * @param filePath    保存路径
     * @param describe    图片描述
     * @param linkAddress 链接地址
     */
    void webUpload(MultipartFile file, String belongTo, String filePath, String describe, String linkAddress);

    /**
     * 网站上传图片
     *
     * @param file        图片文件
     * @param belongTo    类别
     * @param filePath    保存路径
     * @param describe    图片描述
     * @param linkAddress 链接地址
     * @return
     */
    SysFileAttachmentEntity webDoUpload(MultipartFile file, String belongTo, String filePath, String describe, String linkAddress);
}
