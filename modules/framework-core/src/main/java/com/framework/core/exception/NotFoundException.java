package com.framework.core.exception;

/**
 * @ProjectName INNO-BPM
 * @PackageName com.framework.innobpm.core.exception
 * @Title ${TYPE_NAME}
 * @Description 对象没查询到返回的一次
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(){
        super();
    }

    public NotFoundException(String id){
        super("could not found the model with id: "+ id);
    }
}
