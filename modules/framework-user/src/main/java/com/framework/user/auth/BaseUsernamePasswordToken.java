package com.framework.user.auth;

import org.apache.shiro.authc.UsernamePasswordToken;

public class BaseUsernamePasswordToken extends UsernamePasswordToken {
	private static final long serialVersionUID = -4990774492930178295L;

	/**
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 */
	public BaseUsernamePasswordToken(String username, String password) {
		super(username, password);
	}

}
