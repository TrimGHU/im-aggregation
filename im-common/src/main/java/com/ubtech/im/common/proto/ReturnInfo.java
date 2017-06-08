package com.ubtech.im.common.proto;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Copyright © 2017 Ubtech. All rights reserved.
 * 
 * @Title: ReturnInfo.java
 * @Prject: im-common
 * @Package: com.ubtech.im.common.proto
 * @Description: ReturnInfo通用转换接口
 * @author: HuGui
 * @date: 2017年5月23日 下午3:14:10
 * @version: V1.0
 */

public class ReturnInfo {

	private String returnCode;
	private String returnMsg;
	private Map<String, Object> returnMap;

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public Map<String, Object> getReturnMap() {
		return returnMap;
	}

	public void setReturnMap(Map<String, Object> returnMap) {
		this.returnMap = returnMap;
	}

	@Override
	public String toString() {
		String returnInfo = "";

		ObjectMapper mapper = new ObjectMapper();
		try {
			returnInfo = mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return returnInfo;
	}
}
