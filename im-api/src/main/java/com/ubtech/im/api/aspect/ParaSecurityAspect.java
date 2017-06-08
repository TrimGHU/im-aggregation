package com.ubtech.im.api.aspect;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.ubtech.im.common.proto.ReturnInfo;
import com.ubtech.im.common.util.MD5Util;

/**
 * Copyright © 2017 Ubtech. All rights reserved.
 * 
 * @Title: ParameterSecurity.java
 * @Prject: im-security
 * @Package: com.ubtech.im.security
 * @Description: 安全监测过滤
 * @author: HuGui
 * @date: 2017年5月22日 上午10:04:04
 * @version: V1.0
 */

@Aspect
@Order(1)
@Component
public class ParaSecurityAspect {

	private static List<String> NOT_FILTER_METHOD_NAMES = Arrays.asList(new String[] { "imA2OnOffCallback" });

	private static String IM_COMMON_SECURITY_KEY = "IM$SeCrET";

	@SuppressWarnings("unchecked")
	@Around("execution(* com.ubtech.im.api.openapi..*.*(..))")
	public Object verify(ProceedingJoinPoint joinPoint) {
		ReturnInfo returnInfo = new ReturnInfo();

		// 取得方法名称
		String methodName = joinPoint.getSignature().getName();

		// 摄者不需要验证的请求
		if (!NOT_FILTER_METHOD_NAMES.contains(methodName)) {
			// 判断请求的合法性
			Object[] args = joinPoint.getArgs();

			// 取出校验参数
			if (args == null || args.length < 1) {
				returnInfo.setReturnCode("-100000");
				returnInfo.setReturnMsg("Miss Parameter.");
				return returnInfo.toString();
			}

			Map<String,Object> requestMap = (Map<String,Object>)args[0];
			
			String md5Key = (String)requestMap.get("signature");
			String time = (String)requestMap.get("time");

			if (!MD5Util.checkPassword(IM_COMMON_SECURITY_KEY + time, md5Key)) {
				returnInfo.setReturnCode("-100001");
				returnInfo.setReturnMsg("Invalid Parameter.");
				return returnInfo.toString();
			}
		}

		try {
			return joinPoint.proceed(joinPoint.getArgs());
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}

	}

}
