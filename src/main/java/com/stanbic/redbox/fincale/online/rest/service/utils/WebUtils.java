package com.stanbic.redbox.fincale.online.rest.service.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

//@Component
public class WebUtils {

	private HttpServletRequest request;

	//@Autowired
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	//@Bean
	public String getClientIp() {
		String remoteIp = "";
		if (request != null) {
			remoteIp = request.getHeader("X-FORWARDED-FOR");
		}
		if ((remoteIp == null || "".equals(remoteIp)) && request != null) {
			remoteIp = request.getRemoteAddr();
		}
		return remoteIp;
	}

}
