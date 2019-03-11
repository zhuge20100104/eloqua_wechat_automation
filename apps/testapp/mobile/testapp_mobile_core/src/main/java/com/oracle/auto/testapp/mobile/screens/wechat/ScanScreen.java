package com.oracle.auto.testapp.mobile.screens.wechat;

import com.oracle.auto.mobile.components.elements.nativeview.NativeButton;
import com.oracle.auto.mobile.exceptions.MobileExceptionBase;
import com.oracle.auto.testapp.mobile.ancestor.MobileNativeScreenBase;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.annotation.PostConstruct;

/**
 * Created by stepzhou on 7/25/2017.
 */

public class ScanScreen extends MobileNativeScreenBase {
    private NativeButton moreBtn;
    private NativeButton selectBtn;
    private NativeButton selectImage;

    private NativeButton allImagesBtn;
    private NativeButton accountsImages;

    private NativeButton rdServiceAccount;
    private NativeButton subscriptionAccount;
    private NativeButton clientServiceAccount;

    private NativeButton follow;

    public void setFollow(NativeButton follow) {
        this.follow = follow;
    }

    public NativeButton getFollow() {
        //follow = new NativeButton(follow.getLocator(), 3);
        return follow;
    }

    public NativeButton getFollow(String texts) {
        follow.setLocator(String.format(follow.getLocator(), texts));
        return follow;
    }

    public void setSubscriptionAccount(NativeButton subscriptionAccount) {
        this.subscriptionAccount = subscriptionAccount;
    }

    public NativeButton getSubscriptionAccount() {
        //subscriptionAccount = new NativeButton(subscriptionAccount.getLocator(), 5);
        subscriptionAccount.setLocator(String.format(subscriptionAccount.getLocator(), "2017-12-14 17:35"));
        return subscriptionAccount;
    }

    public void setRdServiceAccount(NativeButton rdServiceAccount) {
        this.rdServiceAccount = rdServiceAccount;
    }

    public NativeButton getRdServiceAccount() {
        //rdServiceAccount = new NativeButton(rdServiceAccount.getLocator(),7);
        //甲骨文客户体验演示号
       // rdServiceAccount.setLocator(String.format(rdServiceAccount.getLocator(), "2017-12-14 15:39"));
        //OMCWI研发服务号
        rdServiceAccount.setLocator(String.format(rdServiceAccount.getLocator(), "2017-08-29 13:31"));
        return rdServiceAccount;
    }

    public void setClientServiceAccount(NativeButton clientServiceAccount){
        this.clientServiceAccount = clientServiceAccount;
    }

    public NativeButton getClientServiceAccount(){
        clientServiceAccount = new NativeButton(clientServiceAccount.getLocator(),3);
        return clientServiceAccount;
    }

    public void setAccountsImages(NativeButton accountsImages) {
        this.accountsImages = accountsImages;
    }

    public NativeButton getAccountsImages() {
        return accountsImages;
    }

    public void setAllImagesBtn(NativeButton allImagesBtn) {
        this.allImagesBtn = allImagesBtn;
    }

    public NativeButton getAllImagesBtn() {
        return allImagesBtn;
    }

    public void setSelectImage(NativeButton selectImage) {
        this.selectImage = selectImage;
    }

    public NativeButton getSelectImage() {
        selectImage = new NativeButton(selectImage.getLocator(), 1);
        return selectImage;
    }

    public void setSelectBtn(NativeButton selectBtn) {
        this.selectBtn = selectBtn;
    }

    public NativeButton getSelectBtn() {
        selectBtn = new NativeButton(selectBtn.getLocator(), 1);
        return selectBtn;
    }

    public void setMoreBtn(NativeButton moreBtn) {
        this.moreBtn = moreBtn;
    }

    public NativeButton getMoreBtn() {
        moreBtn = new NativeButton(moreBtn.getLocator(), 10);
        return moreBtn;
    }

    @PostConstruct
    public void init() {
        //registerComp(moreBtn);
        injectMobileDriverToChildComponents(this);
        waitForScreenReady();
    }
}