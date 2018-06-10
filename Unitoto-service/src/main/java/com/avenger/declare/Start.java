package com.avenger.declare;

import java.util.Scanner;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Start {
	
	public static void main(String[] args) throws Exception {
		
		ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(
				"classpath*:spring-declare-register.xml",
				"classpath*:spring-declare-provider.xml",
				"classpath*:spring-declare-service.xml");
		    
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNext()) {
			if ("exit".equals(scanner.next())) {
				break;
			}
		}
		scanner.close();
		classPathXmlApplicationContext.close();
	}
}
