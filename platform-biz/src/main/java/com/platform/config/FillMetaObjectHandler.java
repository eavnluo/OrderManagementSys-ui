package com.platform.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.reflection.MetaObject;

import java.util.Date;

/**
 * @author Created by Tp-wangwh on 2019-9-4.
 */
public class FillMetaObjectHandler implements MetaObjectHandler {
    Log log= LogFactory.getLog(FillMetaObjectHandler.class);
    @Override
    public void insertFill(MetaObject metaObject) {
        Object createDate = getFieldValByName("createDate",metaObject);
        Object delFlag = getFieldValByName("delFlag", metaObject);
        //获取当前登录用户
        try{
            if (null == createDate) {
                setFieldValByName("createDate", new Date(),metaObject);
            }
            if (null == delFlag) {
                setFieldValByName("delFlag", "0", metaObject);
            }
        }catch (Exception e){
            log.error(e.toString());
        }


    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Object lastUpdateNameId = getFieldValByName("updateBy",metaObject);
        Object lastUpdateTime = getFieldValByName("updateDate",metaObject);
        try {

            if (null == lastUpdateTime) {
                setFieldValByName("updateDate", new Date(),metaObject);
            }
        }catch (Exception e) {
            log.error(e.toString());
        }

    }

}
