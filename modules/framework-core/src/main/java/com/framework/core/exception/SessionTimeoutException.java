package com.framework.core.exception;

import com.framework.common.constants.IMessage;

public class SessionTimeoutException extends BusinessException {

    /** serialVersionUID */
    private static final long serialVersionUID = 2332608236621015980L;

    public SessionTimeoutException(String message) {
        super(message);
    }

    public SessionTimeoutException(IMessage errorCode) {
        super(errorCode);
    }

    public SessionTimeoutException(IMessage errorCode, Object[] msgArgs) {
         super(errorCode,msgArgs);
    }


}
