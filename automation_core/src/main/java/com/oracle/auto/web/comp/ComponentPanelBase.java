package com.oracle.auto.web.comp;

import com.oracle.auto.web.comp.interfaces.IComponent;

public class ComponentPanelBase extends ComponentGroupBase {
	public ComponentPanelBase() {
	}

	@Override
	public boolean isEnabled()
	{
    	for (IComponent comp : compList.values()) {
    		if (comp.hideDefault()) continue;
    		if (!comp.isEnabled()) {
    			doSetLastError(comp.getLastError()); // record last error
    			return false;
    		}
    	}
    	return true;
	}
	
	@Override
	public boolean isReady() {
    	for (IComponent comp : compList.values()) {
    		if (comp.hideDefault()) continue;
    		if (!comp.isReady()) {
    			doSetLastError(comp.getLastError()); // record last error
    			return false;
    		}
    	}
    	return true;
	}
}
