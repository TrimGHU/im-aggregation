package com.ubtech.im.service.impl;

import org.springframework.stereotype.Service;

import com.ubtech.im.common.interf.ImService;
import com.ubtech.im.service.third.tencent.TencentIm;

/**   
 * Copyright © 2017 Ubtech. All rights reserved.
 * 
 * @Title: ImServiceImpl.java 
 * @Prject: im-service
 * @Package: com.ubtech.im.service.impl 
 * @Description: IM类的实现方法
 * @author: HuGui   
 * @date: 2017年5月16日 上午10:31:30 
 * @version: V1.0   
 */

@Service(value = "imService")
public class ImServiceImpl implements ImService{

	public void push(String channel, String[] toAccounts, String content) {
		TencentIm.pushMessage(channel, toAccounts, content);
	}
	
	public String isOnline(String channel, String[] toAccounts) {
		return TencentIm.isOnline(channel, toAccounts);
	}
	
	public String getInfo(String channel, String userId){
		return TencentIm.getIMInfo(channel, userId);
	}
}

