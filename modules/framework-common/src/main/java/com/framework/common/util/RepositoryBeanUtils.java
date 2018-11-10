package com.framework.common.util;

/**
 * spring data jpa repository工具类
 * Created by KQY on 2015/8/26.
 */
public class RepositoryBeanUtils {

    /**
     * 根据模型的类型获取repository
     *
     * @param clz 模型的类型
     * @return Object
     */
    public static Object getRepository(Class clz) {
        String clzSimpleName = clz.getSimpleName();
        clzSimpleName = clzSimpleName.substring(0, 1).toLowerCase() + clzSimpleName.substring(1);
        return SpringUtils.getBean(clzSimpleName + "Repository");
    }

}
