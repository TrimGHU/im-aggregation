package com.ubtech.im.api.openapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubtech.im.common.interf.ImService;
import com.ubtech.im.common.proto.ReturnInfo;
import com.ubtech.im.common.util.HttpUtil;

import net.sf.json.JSONObject;

/**
 * 
 * Copyright © 2017 Ubtech. All rights reserved.
 * 
 * @Title: ImApi.java
 * @Prject: im-api
 * @Package: com.ubtech.im.api
 * @Description: im restful open api
 * @author: HuGui
 * @date: 2017年5月17日 上午11:01:37
 * @version: V1.0
 */

@RestController
@RequestMapping("/im")
public class ImApi {

	private static final Logger logger = Logger.getLogger(ImApi.class);

	@Autowired
	private ImService imService;

	@Autowired
	private StringRedisTemplate redisTemplate;

	@RequestMapping(value = "/push", method = RequestMethod.POST)
	public String push(@RequestBody Map<String, Object> requestMap) {
		ReturnInfo returnInfo = new ReturnInfo();

		imService.push((String) requestMap.get("channel"), ((String) requestMap.get("to")).split(","),
				(String) requestMap.get("content"));

		returnInfo.setReturnCode("0");
		returnInfo.setReturnMsg("success");
		return returnInfo.toString();
	}

	@RequestMapping(value = "/isOnline", method = RequestMethod.GET)
	public String isOnline(@RequestParam Map<String, Object> requestMap) {
		return imService.isOnline((String) requestMap.get("channel"), ((String) requestMap.get("accounts")).split(","));
	}

	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/imA2OnOffCallback", method = RequestMethod.POST)
	public void imA2OnOffCallback(HttpServletRequest request) {
		// 解析IM回调数据
		String acceptjson = "";
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader((ServletInputStream) request.getInputStream(), "utf-8"));
			StringBuffer sb = new StringBuffer("");
			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
			br.close();
			acceptjson = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		JSONObject resultJson = JSONObject.fromObject(acceptjson);
		JSONObject infoJson = JSONObject.fromObject(resultJson.getString("Info"));

		String userId = infoJson.getString("To_Account");
		String action = infoJson.getString("Action");
		// 获取原因用处日志处理
		String reason = infoJson.getString("Reason");
		logger.info("ImApi.imA2OnOffCallback -> UserId : " + userId + ", Action : " + action + ", Reason : " + reason);

		// APP_USER状态变更时，无需执行通知操作
		// ROBOT_USER时，需要通知其所有者
		Pattern pattern = Pattern.compile("[0-9]*");
		if (StringUtils.isNotEmpty(userId) && !pattern.matcher(userId).matches()) {

			try {
				String ownersStr = (String) redisTemplate.opsForValue().get(userId);
				if (StringUtils.isEmpty(ownersStr)) {
					ownersStr = HttpUtil.sendGet(
							"http://10.10.1.43:8080/alpha2-web/robot/findBindUserByRobot?equipmentId=" + userId,
							"UTF-8");

					Map<?, ?> map = (Map<?, ?>) JSON.parse(ownersStr);

					if (!new Boolean(map.get("success").toString())) {
						logger.error("ImApi.imA2OnOffCallback -> Error : Query Owners failed");
						return;
					}
					Map<?, ?> dataMap = (Map<?, ?>) map.get("data");
					List<Map<?, ?>> userIdMaps = (List<Map<?, ?>>) dataMap.get("result");

					ownersStr = "";
					for (Map<?, ?> userIdMap : userIdMaps) {
						ownersStr += userIdMap.get("userId");
					}

					if (StringUtils.isEmpty(ownersStr)) {
						logger.error("ImApi.imA2OnOffCallback -> Error : Owners is empty");
						return;
					}
				}

				String[] owners = StringUtils.split(ownersStr, ",");
				
				ObjectMapper mapper = new ObjectMapper();
				Map<String,Object> returnMap = new HashMap<String,Object>();
				Map<String,String> headerMap = new HashMap<String,String>();
				Map<String,String> bodyMap = new HashMap<String,String>();
				headerMap.put("commandId", "2001");
				headerMap.put("versionCode", "1.0");
				bodyMap.put("userId", userId);
				bodyMap.put("state", action);
				returnMap.put("header", headerMap);
				returnMap.put("bodyData", mapper.writeValueAsString(bodyMap));
				
				imService.push("A2", owners, mapper.writeValueAsString(returnMap));
			} catch (IOException e) {
				logger.error("ImApi.imA2OnOffCallback -> Error : " + e.getMessage());
			}
		}
	}

	@RequestMapping(value = "/getInfo", method = RequestMethod.GET)
	public String getInfo(@RequestParam Map<String, Object> requestMap) {
		return imService.getInfo((String) requestMap.get("channel"), (String) requestMap.get("userId"));
	}
}
