package com.oracle.auto.mobile.screen;

import com.oracle.auto.mobile.components.interfaces.IMobileComponent;
import com.oracle.auto.mobile.driver.MobileDriverEx;
import com.oracle.auto.mobile.driver.MobileDriverFactory;
import com.oracle.auto.mobile.exceptions.ScreenInitException;
import com.oracle.auto.web.utility.PropertyConfiger;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

abstract public class ScreenBase implements IScreen {
    private static final int SCREEN_READY_TIMEOUT = PropertyConfiger.instance().getEnvData("screen.all.ready.timeout", 30);
    private static Logger log = Logger.getLogger(ScreenBase.class);
    MobileDriverEx mobileDriverEx;
    private Map<String, IMobileComponent> compList = new HashMap<>();
    private static IScreenMaker screenMaker;
    private String lastReadyError;

    protected ScreenBase() {

        log.debug("[New Screen Object] trying to enter screen: " + this.getClass().getName());
        if (mobileDriverEx == null)
            mobileDriverEx = MobileDriverFactory.instance().getLastMobileDriverSession();

        // Switch to current context when page instantiates.
        // The reason why it's commented:
        // 1). When the page is switched between native UI, the codes will be redundant.
        // 2). When the page is switched between web views, the codes will be redundant.
        // 3). It only make sense when switch between Native UI and Web View.
        // Even for 3), it requires to add a wait time here for the actual context is really switched.
        // Workaround is to set a flag for current context, by judging if it's a context switch or keep the same.
//        AppiumDriver appiumDriver = mobileDriverEx.getDriver();
//        for (Object context : appiumDriver.getContextHandles())
//            appiumDriver.context(context.toString());
    }

    @Override
    public MobileDriverEx getMobileDriverEx() {
        if (mobileDriverEx == null)
            mobileDriverEx = MobileDriverFactory.instance().getLastMobileDriverSession();
        return mobileDriverEx;
    }

    @Override
    public void setMobileDriverEx(MobileDriverEx mobileDriverEx) {
        this.mobileDriverEx = mobileDriverEx;
    }

    @Override
    public void setScreenMaker(IScreenMaker screenMaker) {
        ScreenBase.screenMaker = screenMaker;
    }

    public static <T extends ScreenBase> T screenAs(Class<T> screenClass, Object... initArgs) {
        return screenMaker.screenAs(screenClass, initArgs);
    }

    protected boolean isReady() {
        for (IMobileComponent comp : compList.values()) {
            if (!(comp.isPresent() && comp.isVisible())) {
                lastReadyError = comp.getLastError();
                if (lastReadyError.isEmpty())
                    lastReadyError = "component [" + comp.toString() + "] is not ready.";
                return false;
            }
        }
        return true;
    }

    protected void registerComp(IMobileComponent comp) {
        compList.put("Component " + ThreadLocalRandom.current().nextInt(1000000), comp);
    }

    protected void registerComp(String compName, IMobileComponent comp) {
        compList.put(compName, comp);
    }

    // wait for screen loading and elements rendering
    @Override
    public void waitForScreenReady() {
        //WebDriverHelper.waitForAngular(getMobileDriverEx().getDriver());
        getMobileDriverEx().setImplicitlyWaitTime(1);
        if (!waitForScreenReadyTimout()) {
            getMobileDriverEx().setDefaultImplicitylyWaitTime();
            throw new ScreenInitException(getMobileDriverEx(), getClass().getName(), "time out to wait for it's ready. detail: " + lastReadyError);
        }
        getMobileDriverEx().setDefaultImplicitylyWaitTime();
    }

    // wait for screen loading and elements rendering
    private boolean waitForScreenReadyTimout() {
        return waitForScreenReadyTimout(PropertyConfiger.instance().getEnvData("screen." + getClass().getSimpleName() + ".ready.timeout", SCREEN_READY_TIMEOUT));
    }

    private boolean waitForScreenReadyTimout(int timeoutSeconds) {
        int i = 0;
        int waitUnit = 1;
        while (!isReady()) {
            if ((i += waitUnit) > timeoutSeconds) {
                return false;
            }
            MobileDriverEx.waitFor(waitUnit);
            waitUnit = 1;
        }
        return true;
    }
}