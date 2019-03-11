package com.oracle.auto.web.comp;

import com.oracle.auto.web.comp.interfaces.IComponent;
import org.apache.log4j.Logger;

public class ComponentExclusiveBase extends ComponentGroupBase {
    private static Logger log = Logger.getLogger(ComponentExclusiveBase.class);
    private int lastReadyComponent = -1;

    public ComponentExclusiveBase() {

    }

    @Override
    public boolean isEnabled() {
        if (lastReadyComponent < 0) {
            doSetLastError(getClass().getSimpleName() + " is not ready before calling enabled.");
            return false; // invalid calling, which should have at least one ready component
        }

        // only check the one which is ready (because others might not be ready, which cannot be called Enabled before that)
        return getComps().get(lastReadyComponent).isEnabled();
    }

    protected int doReadyCheck() {
        int index = -1;
        for (IComponent comp : compList.values()) {
            index++;
            if (comp.isReady()) {
                lastReadyComponent = index;
                log.info("the ready component is [" + comp.toString() + "], index = " + index);
                return index;  // if any component is ready, return true;
            } else {
                log.info("the component [" + comp.toString() + "] is not ready, index= " + index + " lastErr=" + comp.getLastError());
            }
        }
        String errors = getClass().getSimpleName() + " is not ready. details: ";
        for (IComponent comp : compList.values()) {
            errors += comp.getLastError() + "\n";
        }
        doSetLastError(errors);
        return -1;
    }

    public int getReadyIndex() {
        waitForReadyEnabled();
        return lastReadyComponent;
    }

    @Override
    public boolean isReady() {
        if (compList.size() <= 0) return true;

        return doReadyCheck() >= 0;
    }


}
