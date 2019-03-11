package com.oracle.auto.testapp.web.pages.MenuServicePages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import com.oracle.auto.web.comp.html.HTMLInput;
import com.oracle.auto.web.comp.html.HTMLTextArea;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by grcao on 8/08/2017.
 */

@Component
public class WelcomeMessageConPage extends TestAppPageBase {

    private HTMLDiv welcomeMsgEnableBtn;
    private HTMLDiv disabledStatus;
    private HTMLDiv enableStatus;
    private HTMLDiv disableStatus;
    private HTMLDiv textTypeBtn;
    private HTMLDiv richMediaTypeBtn;

    private HTMLTextArea configurationArea;
    private HTMLDiv landingPageBox;
    private HTMLDiv landingPageInsertBtn;

    private HTMLDiv richMediaItems;
    private HTMLDiv mediaNewsSelected;
    private HTMLDiv saveBtn;

    private HTMLDiv welcomeContent;

    //For track settings: configuration, text content, linked URL
    private HTMLDiv trackConfig;
    private HTMLInput displayText;
    private HTMLInput embeddedURL;
    private HTMLDiv OKBtn;

    public HTMLDiv getOKBtn() {
        return OKBtn;
    }

    public void setOKBtn(HTMLDiv OKBtn) {
        this.OKBtn = OKBtn;
    }

    public void setEmbeddedURL(HTMLInput embeddedURL) {
        this.embeddedURL = embeddedURL;
    }

    public HTMLInput getEmbeddedURL() {
        return embeddedURL;
    }

    public void setDisplayText(HTMLInput displayText) {
        this.displayText = displayText;
    }

    public HTMLInput getDisplayText() {
        return displayText;
    }

    public void setTrackConfig(HTMLDiv trackConfig) {
        this.trackConfig = trackConfig;
    }

    public HTMLDiv getTrackConfig() {
        return trackConfig;
    }

    @PostConstruct
    public void init() {
        injectPageToChildComponents(this);
        waitForPageReady();
    }

    public void setWelcomeContent(HTMLDiv welcomeContent) {
        this.welcomeContent = welcomeContent;
    }

    public HTMLDiv getWelcomeContent() {
        return welcomeContent;
    }

    public void setWelcomeMsgEnableBtn(HTMLDiv welcomeMsgEnableBtn) {
        this.welcomeMsgEnableBtn = welcomeMsgEnableBtn;
    }

    public HTMLDiv getWelcomeMsgEnableBtn() {
        return welcomeMsgEnableBtn;
    }

    public void setDisabledStatus(HTMLDiv disabledStatus) {
        this.disabledStatus = disabledStatus;
    }

    public HTMLDiv getDisabledStatus() {
        return disabledStatus;
    }

    public void setEnableStatus(HTMLDiv enableStatus) {
        this.enableStatus = enableStatus;
    }

    public HTMLDiv getEnableStatus() {
        return enableStatus;
    }

    public void setDisableStatus(HTMLDiv disableStatus) {
        this.disableStatus = disableStatus;
    }

    public HTMLDiv getDisableStatus() {
        return disableStatus;
    }

    public void setTextTypeBtn(HTMLDiv textTypeBtn) {
        this.textTypeBtn = textTypeBtn;
    }

    public HTMLDiv getTextTypeBtn() {
        return textTypeBtn;
    }

    public void setRichMediaTypeBtn(HTMLDiv richMediaTypeBtn) {
        this.richMediaTypeBtn = richMediaTypeBtn;
    }

    public HTMLDiv getRichMediaTypeBtn() {
        return richMediaTypeBtn;
    }

    public void setConfigurationArea(HTMLTextArea configurationArea) {
        this.configurationArea = configurationArea;
    }

    public HTMLTextArea getConfigurationArea() {
        return configurationArea;
    }

    public void setLandingPageBox(HTMLDiv landingPageBox) {
        this.landingPageBox = landingPageBox;
    }

    public HTMLDiv getLandingPageBox() {
        return landingPageBox;
    }


    public void setLandingPageInsertBtn(HTMLDiv landingPageInsertBtn) {
        this.landingPageInsertBtn = landingPageInsertBtn;
    }

    public HTMLDiv getLandingPageInsertBtn() {
        return landingPageInsertBtn;
    }

    public void setRichMediaItems(HTMLDiv richMediaItems) {
        this.richMediaItems = richMediaItems;
    }

    public HTMLDiv getRichMediaItems() {
        return richMediaItems;
    }

    public HTMLDiv getFirstRichMediaItem() {
        richMediaItems.setLocator(String.format(richMediaItems.getRawLocator(), 0));
        return richMediaItems;
    }

    public HTMLDiv getRandomRichMediaNews() {
        Random random = new Random();
        int richMediaIndex = random.nextInt(5);
        richMediaItems.setLocator(String.format(richMediaItems.getRawLocator(), richMediaIndex));
        return richMediaItems;
    }

    public void setMediaNewsSelected(HTMLDiv mediaNewsSelected) {
        this.mediaNewsSelected = mediaNewsSelected;
    }

    public HTMLDiv getMediaNewsSelected() {
        return mediaNewsSelected;
    }

    public void setSaveBtn(HTMLDiv saveBtn) {
        this.saveBtn = saveBtn;
    }

    public HTMLDiv getSaveBtn() {
        return saveBtn;
    }


    public void waitForSecond(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
