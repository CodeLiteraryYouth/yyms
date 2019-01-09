package com.leanin.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author Administrator
 */
@Aspect
@Component
public class WebLogAspect {

	private static final Logger logger= LoggerFactory.getLogger(WebLogAspect.class);
	
	@Pointcut("execution(public * com.leanin.controller.*.*(..))")
	public void webLog() {		
	}
	
	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) {
		ServletRequestAttributes attribuats=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request=attribuats.getRequest();
		logger.info("URL :"+ request.getRequestURI());
		logger.info("HTTP_METHOD :"+request.getMethod());
		logger.info("IP :"+request.getRemoteAddr());
		Enumeration<String> enu=request.getParameterNames();
		while(enu.hasMoreElements()) {
			String name=enu.nextElement();
			logger.info("name{},value{} ",name,request.getParameter(name));
		}
	}
	
	@AfterReturning(returning="ret",pointcut="webLog()")
	public void doAfterReturning(Object ret) {
		logger.info("RESPONSE :"+ret);
	}
}
