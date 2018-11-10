package com.framework.core.exception;

import com.framework.common.constants.IMessage;

public class ResourceNotFoundException extends SystemException {

    private static final long serialVersionUID = 5651320805529046976L;

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(IMessage errorCode) {
        super(errorCode);
    }

    public ResourceNotFoundException(IMessage errorCode, Object[] msgArgs) {
        super(errorCode,msgArgs);
    }

}
