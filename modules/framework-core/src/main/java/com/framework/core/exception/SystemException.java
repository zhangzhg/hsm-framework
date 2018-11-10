package com.framework.core.exception;

import com.framework.common.constants.IMessage;
import com.framework.core.util.I18nUtils;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;


/**
 * 描述:系统异常
 *
 */
public class SystemException extends RuntimeException {

    private static final long serialVersionUID = 5449107257081827721L;

    private IMessage errorCode;

    private Object[] msgArgs;

    public SystemException(Throwable cause) {
        super(cause);
    }

    public SystemException(String message) {
        super(message);
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemException(IMessage errorCode) {
        this.errorCode = errorCode;
    }

    public SystemException(IMessage errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public SystemException(IMessage errorCode, Object[] msgArgs) {
        this.errorCode = errorCode;
        this.msgArgs = msgArgs;
    }

    public SystemException(IMessage errorCode, Object[] msgArgs, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.msgArgs = msgArgs;
    }

    public Object[] getMsgArgs() {
        return msgArgs;
    }

    public void setMsgArgs(Object[] msgArgs) {
        this.msgArgs = msgArgs;
    }

    /**
     * 获得出错信息. 读取i18N properties文件中Error Code对应的message,并组合参数获得i18n的出错信息.
     */
    public final String getMsg() {
        // 否则用errorCode查询Properties文件获得出错信息
        String message = null;
        try {
            message = I18nUtils.getMessage(this.errorCode, this.getMsgArgs());
        } catch (Exception ex) {
            message = MessageFormat.format("错误代码: {0}, 错误参数: {1}, 国际化消息读取失败!",
                    this.errorCode.getCode(), StringUtils.join(this.getMsgArgs(), "|"));
        }
        return message;
    }

    public final IMessage getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(IMessage errorCode) {
        this.errorCode = errorCode;
    }
}
