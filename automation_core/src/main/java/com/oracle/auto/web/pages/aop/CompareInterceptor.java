package com.oracle.auto.web.pages.aop;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class CompareInterceptor implements MethodInterceptor {

	public CompareInterceptor() {
		// TODO Auto-generated constructor stub
	}

	public Object invoke(MethodInvocation invocation) throws Throwable {
		String obj = invocation.toString();
		String method = invocation.getMethod().toString();
		
		System.out.println("调用方法: " + method + " 细节: " + obj);
		return invocation.proceed();
	}
}
