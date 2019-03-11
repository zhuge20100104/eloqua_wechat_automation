package com.oracle.auto.web.pages.object;

import com.oracle.auto.web.exceptions.PageInitException;
import com.oracle.auto.web.exceptions.WebExceptionBase;
import com.oracle.auto.web.multithread.SeleniumPageFactory;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;
import com.oracle.auto.web.utility.PropertyConfiger;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;


abstract public class PageFactoryBase implements IPageMaker {
	private static Logger log = Logger.getLogger(PageFactoryBase.class);
    private static boolean springEnabled=PropertyConfiger.instance().getEnvData("enable.spring", true);

    final protected SeleniumPageFactory factory;
    // server is reachable or not.


    private ThreadLocal<Boolean> serverUnReachable = new ThreadLocal<Boolean>();

    private static boolean pageEnterSnapshot = PropertyConfiger.instance().getEnvData("take.snapshot.per.page", false);

    protected PageFactoryBase() {
        this.factory = SeleniumPageFactory.instance();
    }

    private String defaultBrowser = PropertyConfiger.instance().getEnvData("default.browser", "googlechrome");

    private <T extends PageBase> T pageAsRaw(Class<T> pageClass,  Object... otherArgs) {
    	T page = null;
    	if (otherArgs.length > 0) {
    		page = doPageAs(pageClass, otherArgs);
    	}
    	else
    		page = doPageAs(pageClass);

    	afterPageInit(page);

    	return page;
    }


    public <T extends PageBase> T pageAs(Class<T> pageClass,  Object... otherArgs) {
    	if (getAppropriateConstructorNoException(pageClass, otherArgs) != null)
    		return pageAsRaw(pageClass, otherArgs);  // try to validate if there's a approiate contructor for the page object
    	
    	if (otherArgs.length > 0) {
    		Object[] initArg = new Object[otherArgs.length + 1];
    		int i = 0;
    		initArg[i++] = factory.getLastPage();
    		for (Object obj : otherArgs)
    			initArg[i++] = obj;
    		return pageAsRaw(pageClass, initArg);
    	}
    	else
    		return pageAsRaw(pageClass, factory.getLastPage());
    }
    
    protected void afterPageInit(PageBase page) {
        if (pageEnterSnapshot && page.page() != null)
            log.info("save enter page [" + page.getClass().getSimpleName() + "] snapshot at" + SeleniumPageFactory.instance().getLastPage().saveScreenshots(page.getClass().getSimpleName() + "_Enter_screenshot_"));
		 
        if (page.page() != null) {
   		 log.info("dump page log when entering " + page.getClass().getSimpleName());
   		 page.page().dumpConsoleLog();
        }
    }


    @SuppressWarnings({ "rawtypes", "unchecked" })
	static private <C extends PageBase> Constructor<C> getAppropriateConstructorNoException(Class<C> c, Object[] initArgs){
        if(initArgs == null)
            initArgs = new Object[0];
        for(Constructor con : c.getDeclaredConstructors()){
            Class[] types = con.getParameterTypes();
            if(types.length!=initArgs.length)
                continue;
            boolean match = true;
            for(int i = 0; i < types.length; i++){
                Class need = types[i], got = initArgs[i].getClass();
                if(!need.isAssignableFrom(got)){
                    if(need.isPrimitive()){
                        match = (int.class.equals(need) && Integer.class.equals(got))
                        || (long.class.equals(need) && Long.class.equals(got))
                        || (char.class.equals(need) && Character.class.equals(got))
                        || (short.class.equals(need) && Short.class.equals(got))
                        || (boolean.class.equals(need) && Boolean.class.equals(got))
                        || (byte.class.equals(need) && Byte.class.equals(got));
                    }else{
                        match = false;
                    }
                }
                if(!match)
                    break;
            }
            if(match)
                return con;
        }
        return null;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	static private <C extends PageBase> Constructor<C> getAppropriateConstructor(Class<C> c, Object[] initArgs){
    	Constructor constructor  = getAppropriateConstructorNoException(c, initArgs);
        if (constructor == null)
        	throw new IllegalArgumentException("Cannot find an appropriate constructor for class " + c + " and arguments " + Arrays.toString(initArgs));
        
        return constructor;
    }

	static  private <C extends PageBase> C getAppropriatePage(Class<C> c){
		return ApplicationContextLocal.get().getBean(c);
	}

    protected <T extends PageBase> T doPageAs(Class<T> pageClass, Object... initArgs) {
    	try {
    		 //pageClass.getDeclaredConstructor(classList);
            IPage page;
			if(springEnabled){
                page =getAppropriatePage(pageClass);
            }
            else{
                Constructor<? extends PageBase> constructor = getAppropriateConstructor(pageClass, initArgs);
                page=constructor.newInstance(initArgs);
            }

    		 page.setPageMaker(this);	// set page factory
    		 if (page.page() == null)
    			 page.setPage(this.lastPage());
			//System.out.println(Thread.currentThread().toString() + "   " + page.toString()+" "+page.page()+" "+page.page().getDriver());
    		 page.loadPageConfig();
    		 if (pageClass.toString().contains(".object.") || pageClass.toString().contains(".objects."))
    			 page.waitForPageReady();
    		 return pageClass.cast(page);
    	} catch (InvocationTargetException ex) {
    		Throwable ex2 = ex.getCause();
    		if (ex2 instanceof WebExceptionBase) {
    			log.error("[Page Factory] fail to init current page to " + pageClass.getName(), ex2);
    			throw (WebExceptionBase)ex2;
    		}
    		log.info("[Page Factory] fail to init current page to " + pageClass.getName());
    		throw new RuntimeException(ex);
    	} catch (BeansException ex) {
            Throwable ex2 = ex.getCause().getCause();
            if (ex2!=null && ex2 instanceof WebExceptionBase) {
                log.error("[Page Factory] fail to init current screen to " + pageClass.getName(), ex2);
                throw (WebExceptionBase) ex2;
            }
            Throwable ex3=ex.getCause();
            if(ex3 instanceof WebExceptionBase){
                log.error("[Page Factory] fail to init current screen to " + pageClass.getName(), ex3);
                throw (WebExceptionBase) ex3;
            }
            log.info("[Screen Factory] fail to init current screen to " + pageClass.getName());
            throw new RuntimeException(ex);
        } catch (Exception ex) {
    		log.info("[Page Factory] fail to init current page to " + pageClass.getName());
    		ex.printStackTrace();
    		throw new RuntimeException(ex);
    	}
    }

    public <T extends PageBase> T resetBrowserAsLoginPage(String browser, Class<T> loginPageClass, Object... initArgs) {
		if (browser.isEmpty()) browser = defaultBrowser;
		if (!factory.isSupportedBrowser(browser)) throw new RuntimeException("**error** unsupported browser: " + browser);

		factory.resetBrowser(browser); // reset browser every time login the page
		factory.openPage(browser);
		return resetCurrentPageAsLoginPage(browser, loginPageClass, initArgs);
    }

    public <T extends PageBase> T resetCurrentPageAsLoginPage(String browser, Class<T> loginPageClass, Object... initArgs) {
		// check if server is unreachable or not
    	if (serverUnReachable.get() != null && serverUnReachable.get()) {
    		log.error("current server is unreachable. directly fail at the early stage to open login page.");
    		throw new RuntimeException("current server is unreachable. directly fail at the early stage to open login page.");
    	}

    	try {
    		return pageAs(loginPageClass, initArgs);
    	} catch (PageInitException ex) {
    		log.error("fail to reset current page to login page.", ex);

    		// set the unreachable flag
    		serverUnReachable.set(Boolean.TRUE);

    		throw ex;
    	}
    }

	public String getDefaultBrowser() {
		return defaultBrowser;
	}

	public String getCurrentBrowser() {
		WebDriverSeleniumPageEx page = factory.getLastPage();
		if (page != null) return page.browserType();

		return "";
	}

	public void setDefaultBrowser(String defaultBrowser) {
		this.defaultBrowser = defaultBrowser;
	}

	public static boolean isPageEnterSnapshot() {
		return pageEnterSnapshot;
	}

	public static void setPageEnterSnapshot(boolean pageEnterSnapshot) {
		PageFactoryBase.pageEnterSnapshot = pageEnterSnapshot;
	}
	public WebDriverSeleniumPageEx lastPage() {
		WebDriverSeleniumPageEx page =  this.factory.getLastPage();
		return page;
	}

}

