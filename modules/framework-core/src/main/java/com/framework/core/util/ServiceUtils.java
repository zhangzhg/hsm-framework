package com.framework.core.util;

import java.io.Serializable;

import com.framework.core.exception.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.common.domain.BaseRelationModel;
import com.framework.common.util.ClassUtils;
import com.framework.common.util.SpringUtils;
import com.framework.core.service.IBaseService;

public class ServiceUtils<T extends BaseRelationModel, ID extends Serializable> {
	
	public static final Logger logger = LoggerFactory
			.getLogger(ServiceUtils.class);

	/**
	 * 获取目标模型对应的service
	 *
	 * @param targetType
	 *            目标模型类型
	 * @return IBaseService
	 */
	public static IBaseService getTargetService(String targetType) {
		String targetServiceName = ClassUtils
				.getLowerCamelAndSingularize(targetType) + "Service";
		IBaseService targetService = SpringUtils.getBean(targetServiceName);
		if (targetService == null) {
			logger.error("can't find principalService : " + targetServiceName);
			throw new SystemException("can't find principalService : "
					+ targetServiceName);
		}
		return targetService;
	}
}
