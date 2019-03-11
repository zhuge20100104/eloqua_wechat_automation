package com.oracle.auto.web.pages.aop;

import com.oracle.auto.web.comp.ext.ExtMessageBoxBase;
import com.oracle.auto.web.comp.interfaces.IComponent;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;
import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

public class AfterClickConfirmExtMessagebox implements AfterReturningAdvice {

	public AfterClickConfirmExtMessagebox() {
		// TODO Auto-generated constructor stub
	}

	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		if (!AdviceHelper.isClickMethod(method, target)) return;
		
		IComponent comp = (IComponent)target;
		WebDriverSeleniumPageEx page = comp.getPage();

		new ExtMessageBoxBase(page).waitUntilExistsAndClickYesIfAny(10);
	}

}
