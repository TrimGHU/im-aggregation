package com.ubtech.im.service.third.tencent;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Copyright © 2017 Ubtech. All rights reserved.
 * 
 * @Title: TencentMsgBody.java
 * @Prject: im-service
 * @Package: com.ubtech.im.service.third.tencent
 * @Description: IM消息体内容
 * @author: HuGui
 * @date: 2017年5月16日 下午3:27:54
 * @version: V1.0
 */

public class TencentMsgBody {
	@JsonProperty("MsgType")
	private String msgType;

	@JsonProperty("MsgContent")
	private Map<String, String> msgContent;

	public TencentMsgBody(String content) {
		super();
		this.msgType = "TIMTextElem";

		Map<String, String> map = new HashMap<String, String>();
		map.put("Text", content);
		this.msgContent = map;
	}

	public String getMsgType() {
		return this.msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public Map<String, String> getMsgContent() {
		return this.msgContent;
	}

	public void setMsgContent(Map<String, String> msgContent) {
		this.msgContent = msgContent;
	}
}
