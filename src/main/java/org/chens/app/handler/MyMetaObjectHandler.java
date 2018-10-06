package org.chens.app.handler;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.chens.core.constants.CommonConstants;
import org.chens.framework.auth.BaseContextHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;


/**
 *   自定义填充公共 创建人，创建时间，修改人、修改时间  字段
 * @author WDP
 *
 */
//@Component 交给BaseMyBatisPlusConfig启动
public class MyMetaObjectHandler extends MetaObjectHandler {
	
	 private Logger logger = LoggerFactory.getLogger(MyMetaObjectHandler.class);



    @Override
	public void insertFill(MetaObject metaObject) {
		logger.debug("*****************保存自动填充******************");
        Date date = new Date();


        if(metaObject.getValue(CommonConstants.BASE_ENTITY_CREATE_TIME)==null)
        {
            setFieldValByName(CommonConstants.BASE_ENTITY_CREATE_TIME, date, metaObject);
        }
        if(metaObject.getValue(CommonConstants.BASE_ENTITY_UPDATE_TIME)==null)
        {
            setFieldValByName(CommonConstants.BASE_ENTITY_UPDATE_TIME, date, metaObject);
        }
        if(metaObject.getValue(CommonConstants.BASE_ENTITY_CREATE_BY)==null)
        {
            setFieldValByName(CommonConstants.BASE_ENTITY_CREATE_BY, BaseContextHandler.getUserId(), metaObject);
        }
        if(metaObject.getValue(CommonConstants.BASE_ENTITY_UPDATE_BY)==null)
        {
            setFieldValByName(CommonConstants.BASE_ENTITY_UPDATE_BY, BaseContextHandler.getUserId(), metaObject);
        }
        if(metaObject.getValue(CommonConstants.BASE_ENTITY_CREATE_BY_NAME)==null)
        {
            setFieldValByName(CommonConstants.BASE_ENTITY_CREATE_BY_NAME, BaseContextHandler.getName(), metaObject);
        }
        if(metaObject.getValue(CommonConstants.BASE_ENTITY_UPDATE_BY_NAME)==null)
        {
            setFieldValByName(CommonConstants.BASE_ENTITY_UPDATE_BY_NAME, BaseContextHandler.getName(), metaObject);
        }
        if(metaObject.getValue(CommonConstants.BASE_ENTITY_TENANT_ID)==null)
        {
            setFieldValByName(CommonConstants.BASE_ENTITY_TENANT_ID, BaseContextHandler.getTenantId(), metaObject);
        }
	}

    @Override
	public void updateFill(MetaObject metaObject) {
		 //更新填充
        logger.debug("********************更新自动填充********************");
        //mybatis-plus版本2.0.9+  
        Date date = new Date();
        setFieldValByName(CommonConstants.BASE_ENTITY_UPDATE_TIME, date, metaObject);
        setFieldValByName(CommonConstants.BASE_ENTITY_UPDATE_BY, BaseContextHandler.getUserId(), metaObject);
        setFieldValByName(CommonConstants.BASE_ENTITY_UPDATE_BY_NAME, BaseContextHandler.getName(), metaObject);

	}

}
