package com.oracle.auto.web.utility;

import com.oracle.auto.web.jbehave_ext.LoadFromClasspathUtf8;
import org.jbehave.core.io.StoryFinder;

import java.util.ArrayList;
import java.util.List;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;


public class ResourceLoaderCross {
//	private static Logger log = Logger.getLogger(ResourceLoader.class);
    // singleton
    private static Object object = new Object();
    private static ResourceLoaderCross instance = null;
    private ResourceLoaderCross() {    }
    public static ResourceLoaderCross instance() {
    	if (instance != null) return instance;
    	synchronized(object) {
    		if (instance == null)
    			instance = new ResourceLoaderCross();
    	}
    	return instance;
    }

	private List<Class<?>> classList = new ArrayList<Class<?>>();

	public void registerModule(Class<?> clazz) {
		classList.add(clazz);
	}
	
	public void registerModuleChains(Class<?> clazzChild, Class<?> clazzParent) {
    	List<Class<?>> classes = new ArrayList<Class<?>>();
    	Class<?> currentClass = clazzChild;
    	while (true) {
    		classes.add(currentClass);
    		if (currentClass.equals(clazzParent)) break;
    		if (currentClass.equals(Object.class)) break;
    		currentClass = (Class<?>)currentClass.getSuperclass();
    	}
    	
    	for (Class<?> clazz : classes)
    		registerModule(clazz);
	}

	// get java steps files
	public String loadResourceAsText(String resourceFile) {
		if (classList.size() <= 0)
			throw new RuntimeException("there's no registered class list for resource loader.");
		
    	for (Class<?> cls : classList) {
    		List<String> lst = new StoryFinder().findClassNames(codeLocationFromClass(cls).getFile(), handlePathList(resourceFile), null);
	    	if (lst.size() >= 1) 
	    		return new LoadFromClasspathUtf8(cls).loadResourceAsText(lst.get(0));
    	}
    	
    	throw new RuntimeException("there're no exactly matched file for resouce: " + resourceFile);
   }

	public List<String> getFileList(String include) {
		return getFileList(include, null);
	}
	
	public List<String> getFileList(String include, String exclude) {
		if (exclude == null) exclude = "";
		
    	List<String> ret = new ArrayList<String>();
    	for (Class<?> cls : classList) {
    		List<String> lst = new StoryFinder().findClassNames(codeLocationFromClass(cls).getFile(), handlePathList(include), handlePathList(exclude));
    		ret.addAll(lst);
    	}

    	return ret;
	}

	public String[] getFileArray(String include) {
    	return getFileArray(include, null);
	}
	
	public String[] getFileArray(String include, String exclude) {
    	return getFileList(include, exclude).toArray(new String[0]);
	}

    private List<String> handlePathList(String filePath) {
    	String[] list = filePath.split(",");
    	ArrayList<String> results = new ArrayList<String>();
    	for (String file : list) {
    		file = file.trim();
    		if (file.isEmpty()) continue;
    		file = "**/" + file;
    		results.add(file);
    	}
    	
    	return results;
    }

}
