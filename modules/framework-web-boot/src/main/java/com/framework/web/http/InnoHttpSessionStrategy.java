package com.framework.web.http;

import org.springframework.session.Session;
import org.springframework.session.web.http.HttpSessionStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by linliangkun on 15/7/21.
 */
public class InnoHttpSessionStrategy implements HttpSessionStrategy {

	private HttpSessionStrategy browser;
	private HttpSessionStrategy api;
	private HttpSessionStrategy token;

	public InnoHttpSessionStrategy(HttpSessionStrategy browser,
			HttpSessionStrategy api, HttpSessionStrategy token) {
		this.browser = browser;
		this.api = api;
		this.token = token;
	}

	@Override
	public String getRequestedSessionId(HttpServletRequest request) {
		return getStrategy(request).getRequestedSessionId(request);
	}

	@Override
	public void onNewSession(Session session, HttpServletRequest request,
			HttpServletResponse response) {
		getStrategy(request).onNewSession(session, request, response);
	}

	@Override
	public void onInvalidateSession(HttpServletRequest request,
			HttpServletResponse response) {
		getStrategy(request).onInvalidateSession(request, response);
	}

	private HttpSessionStrategy getStrategy(HttpServletRequest request) {

		return api.getRequestedSessionId(request) != null ? api : (token
				.getRequestedSessionId(request) != null ? token : browser);

	}
}
