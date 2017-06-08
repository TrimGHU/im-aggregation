package com.ubtech.im.common.interf;

/**   
 * Copyright © 2017 Ubtech. All rights reserved.
 * 
 * @Title: IMService.java 
 * @Prject: im-service
 * @Package: com.ubtech.im.service 
 * @Description: im通用服务接口
 * @author: HuGui   
 * @date: 2017年5月16日 上午10:30:31 
 * @version: V1.0   
 */

public interface ImService {

	/**
	 * 
	 * @Title: push 
	 * @Description: IM内用户之间传递消息
	 * @return: void
	 */
	public void push(String channel, String[] toAccounts, String content);
	
	
	/**
	 * 
	 * @Title: isOnline 
	 * @Description: 查询当前IM中用户状态
	 * @return: String
	 */
	public String isOnline(String channel, String[] toAccounts);
	
	/**
	 * 
	 * @Title: getInfo 
	 * @Description: 获取IM通用信息
	 * @return: String
	 */
	public String getInfo(String channel, String userId);
}

