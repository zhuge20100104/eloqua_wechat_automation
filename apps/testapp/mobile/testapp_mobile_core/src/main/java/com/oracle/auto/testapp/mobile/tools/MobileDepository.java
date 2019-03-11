package com.oracle.auto.testapp.mobile.tools;

import java.util.Set;

/**
 * Created by stepzhou on 8/2/2017.
 */
public class MobileDepository {
    private static final Object lock = new Object();
    private static MobileDepository handler;
    private Set<String> contexts;

    public static MobileDepository instance() {
        if (handler != null) return handler;

        synchronized (lock) {
            if (handler == null)
                handler = new MobileDepository();
        }
        return handler;
    }

    public Set<String> getContexts() { return contexts; }
    public void setContexts(Set<String> contexts) { this.contexts = contexts; }

    public int getContextSize(){
        return contexts.size();
    }
}
