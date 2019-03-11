package com.oracle.auto.web.pages.object;

import com.oracle.auto.web.comp.interfaces.IComponent;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;
import com.oracle.auto.web.utility.PropertyConfiger;
import com.oracle.auto.web.utility.ResourceLoaderCross;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ComponentLoader {
	//private static Logger log = Logger.getLogger(PropertyConfiger.class);
	private static ComponentLoader instance = new ComponentLoader();
    public static ComponentLoader instance() {
    	return instance;
    }

    protected ApplicationContext appContext = null;
    private ComponentLoader() {
    	String compInclude = PropertyConfiger.instance().getEnvData("page.object.comp.list",  "*.comp.xml");
    	String compExclude = PropertyConfiger.instance().getEnvData("page.object.comp.list.exclude",  "");
		appContext = new ClassPathXmlApplicationContext(ResourceLoaderCross.instance().getFileArray(compInclude, compExclude));
    }
    
	public IComponent getCompByData(ComponentData data, WebDriverSeleniumPageEx page) {
		return getCompAsByData(data, page, IComponent.class);
	}
	
	public <T> T getCompAsByData(ComponentData data, WebDriverSeleniumPageEx page, Class<T> classType) {
		Object obj = appContext.getBean(data.type);
		if (obj == null)
			throw new RuntimeException("cannot find component from Spring: " + data.toString());
		
		if (!(obj instanceof IComponent))
			throw new RuntimeException("component is not as expected type: " + data.name + " expected type: IComponent, acctual type: " + obj.toString());
		
		if (!classType.isInstance(obj))
			throw new RuntimeException("component is not as expected type: " + data.name + " expected type: " + classType.toString() + ", acctual type: " + obj.toString());
		
		IComponent comp = (IComponent)obj;
		comp.setPage(page);
		comp.setConfig(data.jsonValue);
		
		return classType.cast(obj);
	}
	
}
