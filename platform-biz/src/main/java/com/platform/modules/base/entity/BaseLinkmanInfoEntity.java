package com.platform.modules.base.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 主要联系人信息表  实体
 *
 * @author zlm
 * @create 2019-09-26 11:09
 */
@Data
@TableName("BASE_LINKMAN_INFO")
public class BaseLinkmanInfoEntity implements Serializable {
    private static final long serialVersionUID = 8241300159010009277L;

    /**
     * 主键
     */
    @TableId
    private String id;
    /**
     * 归属 供应商ID
     */
    private String belongTo;
    /**
     * 联系人姓名
     */
    private String name;
    /**
     * 职位
     */
    private String position;
    /**
     * 电话
     */
    private String phone;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 是否删除
     */
    @TableLogic
    private String delFlag;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新人
     */
    private String updatedBy;
    /**
     * 更新时间
     */
    private Date updatedTime;


}
