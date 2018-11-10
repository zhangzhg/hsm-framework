package com.framework.core.annotation.impl;



import com.framework.common.constants.ErrorCode;
import com.framework.common.util.StringUtils;
import com.framework.core.annotation.CheckField;
import com.framework.core.annotation.MessageWrappingUtils;
import com.framework.core.util.I18nUtils;
//import com.framework.inno.core.util.MessageUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by chenmy on 2015/7/27.
 * 重复输入校验类，两次输入不一致返回错误信息
 */
public class CheckFieldValidator implements ConstraintValidator<CheckField, Object> {
    private CheckField constraintAnnotation;
    //字段名
    private String field;
    //校验字段
    private String verifyField;

    @Override
    public void initialize(CheckField constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
        this.field = constraintAnnotation.field();
        this.verifyField = constraintAnnotation.verifyField();
    }

    @Override
    public boolean isValid(Object validatorObject, ConstraintValidatorContext context) {
        try {
            initializeField(validatorObject);
            //判断两次输入是否一致
            if (!field.equals(verifyField)) {
                String message = I18nUtils.getMessage(ErrorCode.Common.fieldConfirmError);
                MessageWrappingUtils.addMessageToContext("verifyField", message, context);
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 初始校验值
     */
    private void initializeField(Object validatorObject) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (validatorObject == null) {
            return;
        }
        //字段名
        String pwdFieldName = constraintAnnotation.field();
        String confirmationDateFieldName = constraintAnnotation.verifyField();
        //反射获取字段值
        Class clazz = validatorObject.getClass();
        //密码
        Method pwdDateMethod = clazz.getDeclaredMethod(StringUtils.getFieldMethodName(pwdFieldName));
        field = (String) pwdDateMethod.invoke(validatorObject);
        //重新输入值
        Method endDateMethod = clazz.getDeclaredMethod(StringUtils.getFieldMethodName(confirmationDateFieldName));
        verifyField = (String) endDateMethod.invoke(validatorObject);
    }


}