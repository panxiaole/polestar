package com.haier.polestar.datasource.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自定义填充公共 name 字段
 *
 * @author panxiaole
 * @date 2019-04-01
 */
@Component
public class DateMetaObjectHandler implements MetaObjectHandler {

    private final static String UPDATE_TIME = "updateTime";
    private final static String CREATE_TIME = "createTime";

    /**
     * 插入填充，字段为空自动填充
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Object createTime = getFieldValByName(CREATE_TIME, metaObject);
        Object updateTime = getFieldValByName(UPDATE_TIME, metaObject);
        if (createTime == null || updateTime == null) {
            Date date = new Date();
            if (createTime == null) {
                this.setInsertFieldValByName(CREATE_TIME, date, metaObject);
            }
            if (updateTime == null) {
                this.setUpdateFieldValByName(UPDATE_TIME, date, metaObject);
            }
        }
    }

    /**
     * 更新填充
     *
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setUpdateFieldValByName(UPDATE_TIME, new Date(), metaObject);
    }
}
