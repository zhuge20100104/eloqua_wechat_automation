package com.oracle.auto.mobile.screen;

import com.oracle.auto.mobile.driver.MobileDriverEx;
import com.oracle.auto.mobile.driver.MobileDriverFactory;
import com.oracle.auto.mobile.exceptions.MobileExceptionBase;
import com.oracle.auto.mobile.exceptions.ScreenInitException;
import com.oracle.auto.web.exceptions.WebExceptionBase;
import com.oracle.auto.web.pages.object.ApplicationContextLocal;
import com.oracle.auto.web.utility.PropertyConfiger;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

abstract public class ScreenFactoryBase implements IScreenMaker {
    private static Logger log = Logger.getLogger(ScreenFactoryBase.class);
    private static boolean screenEnterSnapshot = PropertyConfiger.instance().getEnvData("take.snapshot.per.screen", false);
    private static boolean springEnabled = PropertyConfiger.instance().getEnvData("enable.spring", true);
    final protected MobileDriverFactory factory;
    // Server is reachable or not.
    private ThreadLocal<Boolean> serverUnReachable = new ThreadLocal<>();

    protected ScreenFactoryBase() { this.factory = MobileDriverFactory.instance();  }

    @SuppressWarnings({"rawtypes", "unchecked"})
    static private <C extends ScreenBase> Constructor<C> getAppropriateConstructorNoException(Class<C> c, Object[] initArgs) {
        if (initArgs == null)
            initArgs = new Object[0];
        for (Constructor con : c.getDeclaredConstructors()) {
            Class[] types = con.getParameterTypes();
            if (types.length != initArgs.length)
                continue;
            boolean match = true;
            for (int i = 0; i < types.length; i++) {
                Class need = types[i], got = initArgs[i].getClass();
                if (!need.isAssignableFrom(got)) {
                    if (need.isPrimitive()) {
                        match = (int.class.equals(need) && Integer.class.equals(got))
                                || (long.class.equals(need) && Long.class.equals(got))
                                || (char.class.equals(need) && Character.class.equals(got))
                                || (short.class.equals(need) && Short.class.equals(got))
                                || (boolean.class.equals(need) && Boolean.class.equals(got))
                                || (byte.class.equals(need) && Byte.class.equals(got));
                    } else {
                        match = false;
                    }
                }
                if (!match)
                    break;
            }
            if (match)
                return con;
        }
        return null;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    static private <C extends ScreenBase> Constructor<C> getAppropriateConstructor(Class<C> c, Object[] initArgs) {
        Constructor constructor = getAppropriateConstructorNoException(c, initArgs);
        if (constructor == null)
            throw new IllegalArgumentException("Cannot find an appropriate constructor for class " + c + " and arguments " + Arrays.toString(initArgs));

        return constructor;
    }

    public static boolean isScreenEnterSnapshot() {
        return screenEnterSnapshot;
    }

    public static void setScreenEnterSnapshot(boolean screenEnterSnapshot) {
        ScreenFactoryBase.screenEnterSnapshot = screenEnterSnapshot;
    }

    private <T extends ScreenBase> T screenAsRaw(Class<T> screenClass, Object... otherArgs) {
        T screen;
        if (otherArgs.length > 0) {
            screen = doScreenAs(screenClass, otherArgs);
        } else {
            screen = doScreenAs(screenClass);
        }
        afterScreenInit(screen);
        return screen;
    }

    public <T extends ScreenBase> T screenAs(Class<T> screenClass, Object... otherArgs) {
        if (getAppropriateConstructorNoException(screenClass, otherArgs) != null)
            return screenAsRaw(screenClass, otherArgs);  // Try to validate if there's a appropriate constructor for the screen object

        if (otherArgs.length > 0) {
            Object[] initArg = new Object[otherArgs.length + 1];
            int i = 0;
            initArg[i++] = factory.getLastMobileDriverSession();
            for (Object obj : otherArgs)
                initArg[i++] = obj;
            return screenAsRaw(screenClass, initArg);
        } else
            return screenAsRaw(screenClass, factory.getLastMobileDriverSession());
    }

    private void afterScreenInit(ScreenBase screen) {
        if (screenEnterSnapshot && screen.getMobileDriverEx() != null)
            log.info("Save enter screen [" + screen.getClass().getSimpleName() + "] snapshot at" + MobileDriverFactory.instance().getLastMobileDriverSession().saveScreenshots(screen.getClass().getSimpleName() + "_Enter_screenshot_"));
    }

    static private <C extends ScreenBase> C getAppropriateScreen(Class<C> c) {
        return ApplicationContextLocal.get().getBean(c);
    }

    private <T extends ScreenBase> T doScreenAs(Class<T> screenClass, Object... initArgs) {
        try {
            Constructor<? extends ScreenBase> constructor = getAppropriateConstructor(screenClass, initArgs); //screenClass.getDeclaredConstructor(classList);
            IScreen screen;
            if (springEnabled) {
                screen = getAppropriateScreen(screenClass);
            } else {
                screen = constructor.newInstance(initArgs);
            }
            screen.setScreenMaker(this); // set screen factory
            if (screen.getMobileDriverEx() == null)
                screen.setMobileDriverEx(this.lastMobileDriverSession());

            if (screenClass.toString().contains(".object.") || screenClass.toString().contains(".objects."))
                screen.waitForScreenReady();
            return screenClass.cast(screen);
        } catch (InvocationTargetException ex) {
            System.err.println("******InvocationTargetException Failed******");
            Throwable ex2 = ex.getCause();
            if (ex2 instanceof WebExceptionBase) {
                log.error("[Screen Factory] fail to init current screen to " + screenClass.getName(), ex2);
                throw (WebExceptionBase) ex2;
            }

            if (ex2 instanceof MobileExceptionBase) {
                log.error("[Screen Factory] fail to init current screen to " + screenClass.getName(), ex2);
                throw (MobileExceptionBase) ex2;
            }

            log.info("[Screen Factory] fail to init current screen to " + screenClass.getName());
            throw new RuntimeException(ex);
        } catch (BeansException ex) {
            System.err.println("******Create Beans Failed******");
            Throwable ex2 = ex.getCause();
            if (ex2 instanceof WebExceptionBase) {
                log.error("[Screen Factory] fail to init current screen to " + screenClass.getName(), ex2);
                throw (WebExceptionBase) ex2;
            }

            if (ex2 instanceof MobileExceptionBase) {
                log.error("[Screen Factory] fail to init current screen to " + screenClass.getName(), ex2);
                throw (MobileExceptionBase) ex2;
            }else if(ex2.getCause() instanceof MobileExceptionBase){
                log.error("[Screen Factory] fail to init current screen to " + screenClass.getName(), ex2);
                throw (MobileExceptionBase) ex2.getCause();
            }

            log.info("[Screen Factory] fail to init current screen to " + screenClass.getName());
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            log.info("[Screen Factory] fail to init current screen to " + screenClass.getName());
            ex.printStackTrace();

            throw new RuntimeException(ex);
        }
    }

    public <T extends ScreenBase> T resetApp(Class<T> firstScreenClass, Object... initArgs) {
        //factory.resetApp();
        factory.launchApp();
        return resetCurrentScreenAsBaseScreen(firstScreenClass, initArgs);
    }

    private <T extends ScreenBase> T resetCurrentScreenAsBaseScreen(Class<T> baseScreenClass, Object... initArgs) {
        // check if server is unreachable or not
        if (serverUnReachable.get() != null && serverUnReachable.get()) {
            log.error("current server is unreachable. directly fail at the early stage to open login screen.");
            throw new RuntimeException("current server is unreachable. directly fail at the early stage to open login screen.");
        }

        try {
            return screenAs(baseScreenClass, initArgs);
        } catch (ScreenInitException ex) {
            log.error("fail to reset current screen to first screen.", ex);
            // set the unreachable flag
            serverUnReachable.set(Boolean.TRUE);
            throw ex;
        }
    }

    private MobileDriverEx lastMobileDriverSession() {
        return this.factory.getLastMobileDriverSession();
    }
}