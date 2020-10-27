/*
 * 项目名称:platform-plus
 * 类名称:SysFileAttachmentServiceImpl.java
 * 包名称:com.platform.modules.sys.service.impl
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020-03-25 14:44:58        zlm     初版做成
 *
 * Copyright (c) 2019-2019 tp软件
 */
package com.platform.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platform.common.utils.FileUploadUtils;
import com.platform.common.utils.Query;
import com.platform.modules.sys.dao.SysFileAttachmentDao;
import com.platform.modules.sys.entity.SysFileAttachmentEntity;
import com.platform.modules.sys.service.SysFileAttachmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

/**
 * Service实现类
 *
 * @author zlm
 * @date 2020-03-25 14:44:58
 */
@Service("sysFileAttachmentService")
public class SysFileAttachmentServiceImpl
        extends ServiceImpl<SysFileAttachmentDao, SysFileAttachmentEntity>
        implements SysFileAttachmentService {

    @Override
    public List<SysFileAttachmentEntity> queryAll(Map<String, Object> params) {
        return baseMapper.queryAll(params);
    }

    @Override
    public Page queryPage(Map<String, Object> params) {
        //排序
        params.put("sidx", "T.id");
        params.put("asc", false);
        Page<SysFileAttachmentEntity> page = new Query<SysFileAttachmentEntity>(params).getPage();
        return page.setRecords(baseMapper.selectSysFileAttachmentPage(page, params));
    }

    @Override
    public boolean add(SysFileAttachmentEntity sysFileAttachment) {
        return this.save(sysFileAttachment);
    }

    @Override
    public boolean update(SysFileAttachmentEntity sysFileAttachment) {
        return this.updateById(sysFileAttachment);
    }

    @Override
    public boolean delete(String id) {
        return this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBatch(String[] ids) {
        return this.removeByIds(Arrays.asList(ids));
    }

    /**
     * 上传附件
     *
     * @param file     文件
     * @param belongTo 文件归属
     * @param filePath 上传路径
     */
    @Override
    public void upload(MultipartFile file, String belongTo, String filePath) {
        // 生成新的文件名
        String newFileName = FileUploadUtils.createNewFileName(file.getOriginalFilename());
        // 文件上传，返回上传路径
        String uploadPath = FileUploadUtils.doUpload(file, filePath, newFileName);
        // 保存上传记录
        SysFileAttachmentEntity attachment = new SysFileAttachmentEntity();
        attachment.setFileName(file.getOriginalFilename());
        attachment.setFileUrl(uploadPath);
        // B -> KB
        attachment.setFileSize(file.getSize() / 1024.0);
        // 文件后缀
        attachment.setFileSuffix(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
        attachment.setDelFlag("0");
        attachment.setBelongTo(belongTo);
        attachment.setCreatedTime(new Date());
        this.add(attachment);
    }

    @Override
    public List<SysFileAttachmentEntity> getListByBelongTo(String belongTo) {
        Map<String, Object> params = new HashMap<>();
        params.put("belongTo", belongTo);
        return queryAll(params);
    }

    /**
     * 网站维护图片
     *
     * @param file        图片文件
     * @param belongTo    类别
     * @param filePath    保存路径
     * @param describe    图片描述
     * @param linkAddress 链接地址
     */
    @Override
    public void webUpload(MultipartFile file, String belongTo, String filePath, String describe, String linkAddress) {
        SysFileAttachmentEntity entity = webDoUpload(file, belongTo, filePath, describe, linkAddress);
        add(entity);
    }

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
    @Override
    public SysFileAttachmentEntity webDoUpload(MultipartFile file, String belongTo, String filePath, String describe, String linkAddress) {
        // 图片重命名
        String fileName = FileUploadUtils.createNewFileName(file.getOriginalFilename());
        // 保存图片到本地
        String uploadPath = FileUploadUtils.doUpload(file, filePath, fileName);
        // 保存图片信息
        SysFileAttachmentEntity entity = new SysFileAttachmentEntity();
        entity.setBelongTo(belongTo);
        entity.setFileName(fileName);
        entity.setFileSuffix(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
        entity.setFileUrl(uploadPath);
        entity.setFileSize(file.getSize() / 1024.0);
        entity.setCreatedTime(new Date());
        entity.setImgDescribe(describe);
        entity.setLinkAddress(linkAddress);
        return entity;
    }
}
