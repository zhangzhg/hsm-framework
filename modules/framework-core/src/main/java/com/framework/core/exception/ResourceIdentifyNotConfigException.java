package com.framework.core.exception;

import com.framework.common.constants.IMessage;

/**
 * @ProjectName INNO-BPM
 * @PackageName com.framework.innobpm.core.exception
 * @Title ${TYPE_NAME}
 * @Description
 */
public class ResourceIdentifyNotConfigException extends SystemException {

    public  ResourceIdentifyNotConfigException(String message){
        super(message);
    }

    public ResourceIdentifyNotConfigException(IMessage errorCode) {
        super(errorCode);
    }

    public ResourceIdentifyNotConfigException(IMessage errorCode, Object[] msgArgs) {
        super(errorCode,msgArgs);
    }

}
