package com.github.sjroom.service;

/**
 * 用户Token
 */
public interface SysUserTokenService {

	/**
	 * 生成token
	 * @param userId  用户ID
	 */
	String createToken(long userId);

	/**
	 * 退出，修改token值
	 */
	void logout(String token);

}
