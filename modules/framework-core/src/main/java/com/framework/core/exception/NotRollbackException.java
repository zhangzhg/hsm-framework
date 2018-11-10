package com.framework.core.exception;

import com.framework.common.constants.IMessage;

public class NotRollbackException extends BusinessException {
	private static final long serialVersionUID = -3344936992682054891L;

	public NotRollbackException(IMessage errorCode) {
		super(errorCode);
	}

	public NotRollbackException(String message) {
		super(message);
	}

	public NotRollbackException(IMessage errorCode, Object[] msgArgs) {
		super(errorCode, msgArgs);
	}

}
