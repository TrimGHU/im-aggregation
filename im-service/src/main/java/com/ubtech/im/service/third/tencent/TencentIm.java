
package com.ubtech.im.service.third.tencent;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tls.sigcheck.tls_sigcheck;
import com.ubtech.im.common.proto.ReturnInfo;

public class TencentIm {

	private static final Logger logger = Logger.getLogger(TencentIm.class);

	public String getSig(String appId, String priKey, String userId) throws IOException {
		tls_sigcheck demo = new tls_sigcheck();

		String sig = "";
		// 使用前请修改动态库的加载路径
		//demo.loadJniLib("C:\\Users\\ubt\\Desktop\\QQIm\\jnisigcheck.dll");
		demo.loadJniLib("/usr/local/lib/jnisigcheck.so");

		int ret = demo.tls_gen_signature_ex2(appId, userId, priKey);

		if (0 != ret) {
			System.out.println("ret " + ret + " " + demo.getErrMsg());
		} else {
			System.out.println("sig:\n" + demo.getSig());
			sig = demo.getSig();
		}
		return sig;
	}

	public boolean checkSig(String sig, String userId, String appId, String pubKey) throws IOException {
		boolean status = false;
		tls_sigcheck demo = new tls_sigcheck();
		int ret = demo.tls_check_signature_ex2(sig, pubKey, appId, userId);
		if (0 != ret) {
			System.out.println("ret " + ret + " " + demo.getErrMsg());
		} else {
			System.out.println(
					"--\nverify ok -- expire time " + demo.getExpireTime() + " -- init time " + demo.getInitTime());
			status = true;
		}
		return status;
	}

	/**
	 * 发送HttpPost请求
	 * 
	 * @param toAccounts
	 *            发送给
	 * @param content
	 *            内容
	 * @return 成功:返回json字符串<br/>
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static void pushMessage(String channel, String[] toAccounts, String content) {
		try {

			Map<String, String> imInfoMap = getIMInfoByChannel(channel);
			String sig = new TencentIm().getSig(imInfoMap.get("APP_ID"), imInfoMap.get("PRIVSTR"),
					imInfoMap.get("ADMIN_ACCOUNT"));

			String sendUrl = "https://console.tim.qq.com/v4/openim/batchsendmsg?usersig=" + sig + "&identifier="
					+ imInfoMap.get("ADMIN_ACCOUNT") + "&sdkappid=" + imInfoMap.get("APP_ID") + "&random="
					+ (int) ((Math.random() * 9 + 1) * 100000000) + "&contenttype=json";

			URL url = new URL(sendUrl);// 创建连接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式
			connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
			connection.connect();
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码

			// 解析 Tencent message body
			TencentBody tb = new TencentBody(toAccounts, content);
			ObjectMapper mapper = new ObjectMapper();
			String tBody = mapper.writeValueAsString(tb);
			out.append(tBody);
			out.flush();
			out.close();

			// 读取响应
			int length = (int) connection.getContentLength();// 获取长度
			InputStream is = connection.getInputStream();
			if (length != -1) {
				byte[] data = new byte[length];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, data, destPos, readLen);
					destPos += readLen;
				}
				String resultStr = new String(data, "UTF-8");

				if (StringUtils.isEmpty(resultStr)) {
					logger.error("TencentIm.pushMessage -> Error : IM Return Message is Empty");
					return;
				}

				Map<String, Object> resultMap = (Map<String, Object>) JSON.parse(resultStr);
				String actionStatus = (String) resultMap.get("ActionStatus");

				if ("OK".equals(actionStatus)) {
					logger.info("TencentIm.pushMessage -> Success : " + Arrays.toString(toAccounts) + " pushed");
				} else {
					List<String> accounts = Arrays.asList(toAccounts);

					if (resultMap.containsKey("ErrorList")) {
						Map<String, String>[] errorMaps = (HashMap<String, String>[]) resultMap.get("ErrorList");
						for (Map<String, String> errorMap : errorMaps) {
							accounts.remove(errorMap.get("To_Account").toString());
							logger.error("TencentIm.pushMessage -> Error : " + errorMap.get("To_Account").toString()
									+ " push failed " + errorMap.get("ErrorCode").toString());
						}
						logger.info("TencentIm.pushMessage -> Success : " + accounts.toString() + " pushed");
					} else {
						logger.error("TencentIm.pushMessage -> Error : " + accounts + " push failed "
								+ resultMap.get("ErrorCode").toString());
					}

				}
			}
		} catch (IOException e) {
			logger.error("TencentIm.pushMessage -> Error : " + e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public static String isOnline(String channel, String[] toAccounts) {
		ReturnInfo returnInfo = new ReturnInfo();

		try {
			Map<String, String> imInfoMap = getIMInfoByChannel(channel);
			String sig = new TencentIm().getSig(imInfoMap.get("APP_ID"), imInfoMap.get("PRIVSTR"),
					imInfoMap.get("ADMIN_ACCOUNT"));

			String sendUrl = "https://console.tim.qq.com/v4/openim/querystate?usersig=" + sig + "&identifier="
					+ imInfoMap.get("ADMIN_ACCOUNT") + "&sdkappid=" + imInfoMap.get("APP_ID") + "&random="
					+ (int) ((Math.random() * 9 + 1) * 100000000) + "&contenttype=json";

			URL url = new URL(sendUrl);// 创建连接
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式
			connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
			connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
			connection.connect();
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码

			Map<String, String[]> map = new HashMap<String, String[]>();
			ObjectMapper mapper = new ObjectMapper();
			map.put("To_Account", toAccounts);
			out.append(mapper.writeValueAsString(map));
			out.flush();
			out.close();

			// 读取响应
			int length = (int) connection.getContentLength();
			InputStream is = connection.getInputStream();
			if (length != -1) {
				byte[] data = new byte[length];
				byte[] temp = new byte[512];
				int readLen = 0;
				int destPos = 0;
				while ((readLen = is.read(temp)) > 0) {
					System.arraycopy(temp, 0, data, destPos, readLen);
					destPos += readLen;
				}
				String resultStr = new String(data, "UTF-8");

				if (StringUtils.isEmpty(resultStr)) {
					returnInfo.setReturnCode("-200000");
					returnInfo.setReturnMsg("Error : IM Return Message is Empty.");
					return returnInfo.toString();
				}

				Map<String, Object> resultMap = (Map<String, Object>) JSON.parse(resultStr);
				String errorCode = String.valueOf(resultMap.get("ErrorCode"));

				if ("0".equals(errorCode)) {
					Map<String, Object> returnMap = new HashMap<String, Object>();
					List<Map<String, String>> stateResultList = (List<Map<String, String>>) resultMap
							.get("QueryResult");
					for (Map<String, String> stateResultMap : stateResultList) {
						returnMap.put(stateResultMap.get("To_Account"), stateResultMap.get("State"));
					}
					returnInfo.setReturnCode("0");
					returnInfo.setReturnMsg("success");
					returnInfo.setReturnMap(returnMap);
					return returnInfo.toString();
				} else {
					returnInfo.setReturnCode("-200001");
					returnInfo.setReturnMsg("Error : IM Return Code is " + errorCode);
					return returnInfo.toString();
				}

			}
		} catch (Exception e) {
			logger.error("TencentIm.pushMessage -> Error : " + e.getMessage());
		}

		return returnInfo.toString();
	}

	public static String getIMInfo(String channel, String userId) {
		ReturnInfo returnInfo = new ReturnInfo();
		returnInfo.setReturnCode("0");
		returnInfo.setReturnMsg("success");

		Map<String, Object> returnMap = new HashMap<String, Object>();

		try {
			Map<String, String> imInfoMap = getIMInfoByChannel(channel);

			String sig = new TencentIm().getSig(imInfoMap.get("APP_ID"), imInfoMap.get("PRIVSTR"), userId);
			returnMap.put("accountType", imInfoMap.get("ACCOUNT_TYPE"));
			returnMap.put("userSig", sig);
			returnMap.put("appidAt3rd", imInfoMap.get("APP_ID"));
			returnInfo.setReturnMap(returnMap);
		} catch (IOException e) {
			logger.error("TencentIm.getIMInfo -> Error : " + e.getMessage());
		}

		return returnInfo.toString();
	}

	/**
	 * 
	 * @Title: getIMInfoByChannel
	 * @Description: 根据channel获取不同IM频道参数
	 * @return: Map<String,String>
	 */
	public static Map<String, String> getIMInfoByChannel(String channel) {
		Map<String, String> map = new HashMap<String, String>();

		switch (channel) {
		case "A2":
			map.put("APP_ID", TencentConstant.ALPHA2.APP_ID);
			map.put("ADMIN_ACCOUNT", TencentConstant.ALPHA2.ADMIN_ACCOUNT);
			map.put("ACCOUNT_TYPE", TencentConstant.ALPHA2.ACCOUNT_TYPE);
			map.put("PRIVSTR", TencentConstant.ALPHA2.PRIVSTR);
			map.put("PUBSTR", TencentConstant.ALPHA2.PUBSTR);
			break;
		default:
			map.put("APP_ID", TencentConstant.ALPHA2.APP_ID);
			map.put("ADMIN_ACCOUNT", TencentConstant.ALPHA2.ADMIN_ACCOUNT);
			map.put("ACCOUNT_TYPE", TencentConstant.ALPHA2.ACCOUNT_TYPE);
			map.put("PRIVSTR", TencentConstant.ALPHA2.PRIVSTR);
			map.put("PUBSTR", TencentConstant.ALPHA2.PUBSTR);
			break;
		}

		return map;
	}
}
