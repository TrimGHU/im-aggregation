package com.ubtech.im.service.third.tencent;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Copyright © 2017 Ubtech. All rights reserved.
 * 
 * @Title: MessageBody.java
 * @Prject: im-service
 * @Package: com.ubtech.im.service.third.tencent
 * @Description: IM消息体
 * @author: HuGui
 * @date: 2017年5月16日 下午2:07:43
 * @version: V1.0
 */

public class TencentBody {
	@JsonProperty("To_Account")
	private String[] toAccounts;	

	@JsonProperty("MsgRandom")
	private Integer msgRandom;

	@JsonProperty("MsgBody")
	private TencentMsgBody[] msgBody;

	public TencentBody(String[] toAccounts, String content) {
		super();
		this.toAccounts = toAccounts;
		this.msgRandom = (int) Math.floor(Math.random() * 1000000000);

		TencentMsgBody tmb = new TencentMsgBody(content);
		this.msgBody = new TencentMsgBody[] { tmb };
	}

	public String[] getToAccounts() {
		return toAccounts;
	}

	public void setToAccounts(String[] toAccounts) {
		this.toAccounts = toAccounts;
	}

	public Integer getMsgRandom() {
		return msgRandom;
	}

	public void setMsgRandom(Integer msgRandom) {
		this.msgRandom = msgRandom;
	}

	public TencentMsgBody[] getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(TencentMsgBody[] msgBody) {
		this.msgBody = msgBody;
	}
}
