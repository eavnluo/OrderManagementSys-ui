package com.platform.modules.base.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @Description: 状态分组
 *
 * @author wangxinpeng
 * @date 2019-09-16 10:34
 */
@Data
public class BaseStatusGroupEntity implements Serializable {

    /**
     * 分组名
     */
    private String label;

    /**
     * 分组值
     */
    private String value;

    /**
     * 组内状态
     */
    private List<BaseStatusCodeEntity> statusList;
}
