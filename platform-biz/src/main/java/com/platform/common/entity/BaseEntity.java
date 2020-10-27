package com.platform.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Created by Tp-wangwh on 2019-9-4.
 */
public  abstract  class BaseEntity implements Serializable {

    @TableField("create_date")
    private Date createDate;
    @TableField("update_date")
    private Date updateDate;

}
