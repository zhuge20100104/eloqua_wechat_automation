package com.oracle.auto.testapp.mobile.screens.wechat;

import com.oracle.auto.mobile.components.elements.nativeview.NativeButton;
import com.oracle.auto.mobile.exceptions.MobileExceptionBase;
import com.oracle.auto.testapp.mobile.ancestor.MobileNativeScreenBase;
import com.oracle.auto.testapp.mobile.ancestor.MobileScreenBase;
import com.oracle.auto.web.exceptions.WebDriverExceptionEx;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * Created by stepzhou on 7/25/2017.
 */

public class HomeScreen extends MobileNativeScreenBase {
    private static final int ELEMENTWAITTIMEOUT = 30;

    private NativeButton oraAccount;
    private NativeButton topRightOptions;
    private NativeButton scanBtn;

    private NativeButton unfollow;
    private NativeButton confirm;

    private NativeButton back;

    private NativeButton backPrevious;

    public void setBackPrevious(NativeButton backPrevious){
        this.backPrevious = backPrevious;
    }

    public NativeButton getBackPrevious(){
        return backPrevious;
    }

    public NativeButton getBackPrevious(String text){
        backPrevious.setLocator(String.format(backPrevious.getLocator(),text));
        return backPrevious;
    }

    public void setBack(NativeButton back) {
        this.back = back;
    }

    public NativeButton getBack() {
        return back;
    }

    public NativeButton getBack(int index) {
        back = new NativeButton(back.getLocator(), 1);
        return back;
    }

    public void setConfirm(NativeButton confirm) {
        this.confirm = confirm;
    }

    public NativeButton getConfirm() {
        return confirm;
    }

    public NativeButton getConfirm(String texts){
        confirm.setLocator(String.format(confirm.getLocator(),texts));
        return confirm;
    }

    public void setUnfollow(NativeButton unfollow) {
        this.unfollow = unfollow;
    }

    public NativeButton getUnfollow() {
        return unfollow;
    }

    public NativeButton getUnfollow(String texts) {
        unfollow.setLocator(String.format(unfollow.getLocator(), texts));
        return unfollow;
    }

    public void setScanBtn(NativeButton scanBtn) {
        this.scanBtn = scanBtn;
    }

    public NativeButton getScanBtn() {
        scanBtn = new NativeButton(scanBtn.getLocator(), 2);
        return scanBtn;
    }

    public void setTopRightOptions(NativeButton topRightOptions) {
        this.topRightOptions = topRightOptions;
    }

    public NativeButton getTopRightOptions() {
        return topRightOptions;
    }

    public void setOraAccount(NativeButton oraAccount) {
        this.oraAccount = oraAccount;
    }

    public NativeButton getOraAccount() {
        return oraAccount;

}
    public NativeButton getOraAccount(String name) {
        oraAccount.setLocator(String.format(oraAccount.getLocator(), name));
        return oraAccount;
    }

    @PostConstruct
    public void init() {
        //registerComp(oraAccount);
        //registerComp(topRightOptions);
        injectMobileDriverToChildComponents(this);
        waitForScreenReady();
    }

    public void waitForShort(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void click(String name) {
        try {
            new WebDriverWait(getMobileDriverEx().getDriver(), ELEMENTWAITTIMEOUT).until(ExpectedConditions.presenceOfElementLocated((getMobileDriverEx().parseBy("name=" + name))));
            MobileElement mobileElement = (MobileElement) getMobileDriverEx().getDriver().findElementsByName(name).get(0);
            mobileElement.click();
        } catch (Exception ex) {
            throw new MobileExceptionBase(getMobileDriverEx(), "Element " + name + " is not presented.");
        }
    }

    public void click1(String name) {
        try {
            new WebDriverWait(getMobileDriverEx().getDriver(), ELEMENTWAITTIMEOUT).until(ExpectedConditions.presenceOfElementLocated((getMobileDriverEx().parseBy("xpath=//android.view.View[contains(@text, 'OMCWI研发服务号')]"))));
            MobileElement mobileElement = (MobileElement) getMobileDriverEx().getDriver().findElementsByXPath("//android.view.View[contains(@text, 'OMCWI研发服务号')]").get(0);
            mobileElement.click();
        } catch (Exception ex) {
            throw new MobileExceptionBase(getMobileDriverEx(), "Element " + name + " is not presented.");
        }
    }


}