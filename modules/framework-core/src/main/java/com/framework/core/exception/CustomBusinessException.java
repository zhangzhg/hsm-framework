package com.framework.core.exception;

import com.framework.common.constants.ICodeMessage;

public class CustomBusinessException extends RuntimeException {
    private ICodeMessage codeMessage;

    public CustomBusinessException(ICodeMessage codeMessage) {
        this.codeMessage = codeMessage;
    }

    public ICodeMessage getCodeMessage() {
        return codeMessage;
    }

    public void setCodeMessage(ICodeMessage codeMessage) {
        this.codeMessage = codeMessage;
    }
}
