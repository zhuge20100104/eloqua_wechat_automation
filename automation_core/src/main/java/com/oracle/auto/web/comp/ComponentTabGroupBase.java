package com.oracle.auto.web.comp;

import com.oracle.auto.web.comp.interfaces.IComponent;
import org.apache.log4j.Logger;

// all sub components are visible/enabled except one.
public class ComponentTabGroupBase extends ComponentGroupBase {
	private static Logger log = Logger.getLogger(ComponentTabGroupBase.class);
	private int lastNotReadyComponent = -1;
	
	public ComponentTabGroupBase() {

	}

	@Override
	public boolean isEnabled()
	{
		// all components except the selected are enabled
		int index = -1;
		for (IComponent comp : compList.values()) {
			if (lastNotReadyComponent == ++index) continue; // skip selected component
			if (!comp.isEnabled()) {
				log.info("the component is not enabled [" + comp.toString() + "], index = " + index);
				return false;
			}
		}
		
		return true;
	}
	
	protected int doReadyCheck() {
		lastNotReadyComponent = -1;
		
		int index = -1;
    	for (IComponent comp : compList.values()) {
    		index++;
    		if (comp.isReady()) continue;
    		 
    		if (lastNotReadyComponent == -1) {
    			lastNotReadyComponent = index;
    			log.info("the first selected component is [" + comp.toString() + "], index = " + index);
    		}
    		else {
    			// there already one selected component, it's invalid.
    			log.error("the previous selected component is [" + getComps().get(lastNotReadyComponent).toString() + "], index = " + lastNotReadyComponent);
    			log.error("there's new selected component is [" + getComps().get(index).toString() + "], index = " + index);
    			
    			return -1;
    		}
    	}
    	
    	// has no selected component
    	if (lastNotReadyComponent == -1) {
        	String errors = getClass().getSimpleName() + " is not ready. because there's no one tab is selected.";
        	doSetLastError(errors);
        	
        	return -1;
    	}
    	
    	// has selected component
    	log.info("the component [" + getComps().get(lastNotReadyComponent).toString() + "] is not ready, index= " + lastNotReadyComponent + " lastErr=" + getComps().get(lastNotReadyComponent).getLastError());
    	return lastNotReadyComponent;
	}
	
	public int getSelectedIndex() {
		//waitForReadyEnabled();
		return lastNotReadyComponent;
	}

	@Override
	public boolean isReady() {
		if (compList.size() <= 0) return true;
		
    	return doReadyCheck() >= 0;
	}
}
