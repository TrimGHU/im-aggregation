package com.ubtech.im.api.aspect;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Copyright © 2017 Ubtech. All rights reserved.
 * 
 * @Title: LogAspect.java
 * @Prject: im-api
 * @Package: com.ubtech.im.api.aspect
 * @Description: 日志切片操作
 * @author: HuGui
 * @date: 2017年5月24日 上午10:21:08
 * @version: V1.0
 */

@Aspect
@Order(5)
@Component
public class LogAspect {

	private static final Logger logger = Logger.getLogger(LogAspect.class);

	@Pointcut("execution(* com.ubtech.im.api.openapi..*.*(..))")
	public void webLog() {
	}
	
	
	@Before( "webLog()" )
    public void doBefore( JoinPoint joinPoint ) throws Throwable
    {
		logger.info("ClassMethod:" + joinPoint.getSignature().getDeclaringTypeName() + "."
				+ joinPoint.getSignature().getName() + " , " + "Args:" + Arrays.toString(joinPoint.getArgs()));
    }
}
