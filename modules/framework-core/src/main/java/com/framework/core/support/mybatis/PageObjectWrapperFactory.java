package com.framework.core.support.mybatis;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectionException;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;

/**
 * mybatis结果集特殊处理转出jpa-page对象
 * @author linliangkun
 * @date 15/8/23
 */
public class PageObjectWrapperFactory extends DefaultObjectWrapperFactory {

    @Override
    public boolean hasWrapperFor(Object object) {
        if(object.getClass().getName().equals("CustomPageImpl"))
            return true;
        else return false;
    }

    @Override
    public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
        if(object.getClass().getName().equals("CustomPageImpl"))
            return   new PageWrapper(metaObject, object);
       else
            throw new ReflectionException("The DefaultObjectWrapperFactory should never be called to provide an ObjectWrapper.");
    }
}
