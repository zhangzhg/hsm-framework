package com.framework.web.handler;


import com.framework.common.constants.ApiJsonResult;
import com.framework.common.domain.ErrorMessage;
import com.framework.common.util.StringUtils;
import com.framework.core.exception.*;
import com.framework.core.util.I18nUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;

import java.io.IOException;

/**
 * @Title ${TYPE_NAME}
 * @Description 全局处理异常信息
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private String appNo = "000-";

    /**
     * 400-拦截所有异常
     *
     * @param ex
     * @return
     * @throws IOException
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ApiJsonResult handleAllException(Exception ex) {
        ApiJsonResult error = new ApiJsonResult();
        error.setCode("-1");
        error.setMsg("服务器发生异常，请联系管理员");
        logger.error(ex.getMessage(), ex);
        return error;
    }

    /**
     * 400-业务异常
     *
     * @param ex
     * @return
     * @throws IOException
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BusinessException.class)
    public ApiJsonResult handleBusinessException(BusinessException ex) throws IOException {
        ApiJsonResult error = new ApiJsonResult();
        error.setCode("-1");
        error.setMsg(ex.getMsg());
        logger.error(ex.getMessage(), ex);
        return error;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomBusinessException.class)
    public ApiJsonResult handleBusinessException(CustomBusinessException ex) throws IOException {
        ApiJsonResult error = new ApiJsonResult();
        error.setResult(ex.getCodeMessage());
        logger.error(ex.getMessage(), ex);
        return error;
    }

    /**
     * 400-参数验证异常
     *
     * @param ex
     * @return
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiJsonResult handleIllegalArgumentException(IllegalArgumentException ex) {
        ApiJsonResult error = new ApiJsonResult();
        error.setCode("-1");
        error.setMsg("服务器发生异常，参数验证失败");
        logger.error(ex.getMessage(), ex);
        return error;
    }


    /**
     * 404-找不到异常
     *
     * @param ex
     * @return
     * @throws IOException
     */
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceIdentifyNotConfigException.class)
    public ApiJsonResult handleResourceIdentifyNotConfigException(Exception ex) throws IOException {
        ApiJsonResult error = new ApiJsonResult();
        error.setCode("-1");
        error.setMsg("服务器发生异常，资源找不到");
        logger.error(ex.getMessage(), ex);
        return error;
    }

}
