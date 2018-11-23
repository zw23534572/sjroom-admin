package com.github.sjroom.common;

import org.springframework.beans.factory.annotation.Value;

/**
 * 系统信息
 * 
 * @author luobinwen
 *
 */
public class ApplicationInfo {

	private int systemCode = 0;

	public int getSystemCode() {
		return systemCode;
	}

	public Integer getDefaultErrorCode() {
		return Integer.valueOf(systemCode + "500");
	}

	public Integer getDefaultSuccessCode() {
		return Integer.valueOf(systemCode + "200");
	}

	@Value("${system_code}")
	public void setApplicationInfo(Integer systemCode) {
		INFO.systemCode = systemCode;
	}

	private static final ApplicationInfo INFO = new ApplicationInfo();

	public static ApplicationInfo instance() {
		return INFO;
	}

}
