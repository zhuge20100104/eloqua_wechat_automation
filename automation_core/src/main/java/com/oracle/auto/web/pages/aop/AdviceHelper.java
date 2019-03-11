package com.oracle.auto.web.pages.aop;

import com.oracle.auto.web.comp.interfaces.IClickable;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;

class AdviceHelper {
	private static Logger log = Logger.getLogger(AdviceHelper.class);
	static boolean  isClickMethod(Method method, Object target) throws Throwable  {
		if ((target instanceof IClickable) && method.equals(IClickable.class.getMethod("click"))) {
			return true;
		}
		
		log.info("ignore calling: method: " + method.toString() + " target: " + target.toString());
		return false;
	}
}
