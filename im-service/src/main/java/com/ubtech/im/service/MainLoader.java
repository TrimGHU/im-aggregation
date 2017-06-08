package com.ubtech.im.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Copyright © 2017 Ubtech. All rights reserved.
 * 
 * @Title: MainLoader.java
 * @Prject: im-service
 * @Package: com.ubtech.im.service
 * @Description: TODO
 * @author: HuGui
 * @date: 2017年5月24日 下午2:21:12
 * @version: V1.0
 */

public class MainLoader {

	public static void main(String[] args) {
		final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:*META-INF/spring/dubbo-services.xml");
		System.out.println(">>>>>>" + context + ":::" + context.isRunning());
		try {
			synchronized (MainLoader.class) {
				MainLoader.class.wait();
			}
		} catch (Exception e) {
		}
	}
}
