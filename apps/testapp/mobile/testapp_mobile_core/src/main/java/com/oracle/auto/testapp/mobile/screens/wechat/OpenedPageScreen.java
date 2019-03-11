package com.oracle.auto.testapp.mobile.screens.wechat;

import com.oracle.auto.mobile.assertions.MobileAssert;
import com.oracle.auto.mobile.components.elements.nativeview.NativeButton;
import com.oracle.auto.mobile.driver.MobileDriverFactory;
import com.oracle.auto.mobile.exceptions.MobileExceptionBase;
import com.oracle.auto.testapp.mobile.ancestor.MobileNativeScreenBase;
import com.oracle.auto.web.utility.WebAssert;
import io.appium.java_client.AppiumDriver;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Created by stepzhou on 7/25/2017.
 */

public class OpenedPageScreen extends MobileNativeScreenBase {
    private NativeButton moreBtn;
    private NativeButton copyLinkBtn;

    private NativeButton openInBrowser;


    public NativeButton getOpenInBrowser() {
        openInBrowser.setLocator(String.format(openInBrowser.getLocator(), "在浏览器打开"));
        return openInBrowser;
    }

    public void setOpenInBrowser(NativeButton openInBrowser) {
        this.openInBrowser = openInBrowser;
    }

    public void setMoreBtn(NativeButton moreBtn) {
        this.moreBtn = moreBtn;
    }

    public NativeButton getMoreBtn() {
//        AppiumDriver driver = getMobileDriverEx().getDriver();
//        System.out.println(driver.getContext());
//        driver.getContextHandles();
        moreBtn.setLocator(String.format(moreBtn.getLocator(), "更多"));
        return moreBtn;
    }

    public void setCopyLinkBtn(NativeButton copyLinkBtn) {
        this.copyLinkBtn = copyLinkBtn;
    }

    public NativeButton getCopyLinkBtn() {
        copyLinkBtn.setLocator(String.format(copyLinkBtn.getLocator(), "复制链接"));
        return copyLinkBtn;
    }

    // public boolean verifyAPPPageTitlePresent(){
    public NativeButton getAPPPageTitle() {
        // String locator=String.format("id=com.tencent.mm:id/ix",title);
        NativeButton appPageTile = new NativeButton("id=com.tencent.mm:id/ix");
        appPageTile.setMobileDriverEx(getMobileDriverEx());
        return appPageTile;
        //NativeButton appPageTile = new NativeButton("xpath=//android.widget.TextView[contains(@text, '%s')]");

    }

    public void verifyAPPPageTitlePresent() {
        MobileAssert.assertTrue("APP Page title is not present", getAPPPageTitle().isPresent());
    }

    public void verifyURL_service_external_send_by_campaign(String contentURL, String identifier) {
        String getUrl = getOpenedURL();

        //  WebAssert.assertTrue("Failed to open specified URL " + contentURL, getUrl.contains("http://cn.bing.com/"));
        String tailURL = getUrl.substring(getUrl.indexOf(".com/") + 5);

        if (identifier.equalsIgnoreCase("None")) {
            MobileAssert.assertTrue("Incorrect URL when identifier = None, now is: " + getUrl, tailURL.contains("CampaignId="));
            MobileAssert.assertFalse("Incorrect URL when identifier = WeChat ID, now is: " + getUrl, tailURL.contains("openid="));
            MobileAssert.assertFalse("Incorrect URL when identifier = WeChat ID, now is: " + getUrl, tailURL.contains("unionid="));
        } else if (identifier.equalsIgnoreCase("Eloqua ID")) {
            MobileAssert.assertTrue("Incorrect URL when identifier = Eloqua ID, now is: " + getUrl, tailURL.length() > 0);
            MobileAssert.assertTrue("Incorrect URL when identifier = None, now is: " + getUrl, tailURL.contains("CampaignId="));
            MobileAssert.assertFalse("Incorrect URL when identifier = WeChat ID, now is: " + getUrl, tailURL.contains("openid="));
            MobileAssert.assertFalse("Incorrect URL when identifier = WeChat ID, now is: " + getUrl, tailURL.contains("unionid="));
        } else if (identifier.equalsIgnoreCase("WeChat ID")) {
            MobileAssert.assertTrue("Incorrect URL when identifier = None, now is: " + getUrl, tailURL.contains("CampaignId="));
            MobileAssert.assertTrue("Incorrect URL when identifier = WeChat ID, now is: " + getUrl, tailURL.contains("openid="));
            // MobileAssert.assertTrue("Incorrect URL when identifier = WeChat ID, now is: " + getUrl, tailURL.contains("unionid="));
        } else if (identifier.equalsIgnoreCase("WeChat Profile")) {
            MobileAssert.assertTrue("Incorrect URL when identifier = None, now is: " + getUrl, tailURL.contains("CampaignId="));
            MobileAssert.assertTrue("Incorrect URL when identifier = WeChat Profile, now is: " + getUrl, tailURL.contains("openid="));
            MobileAssert.assertTrue("Incorrect URL when identifier = WeChat Profile, now is: " + getUrl, tailURL.contains("nickname="));
            MobileAssert.assertTrue("Incorrect URL when identifier = WeChat Profile, now is: " + getUrl, tailURL.contains("province="));
            MobileAssert.assertTrue("Incorrect URL when identifier = WeChat Profile, now is: " + getUrl, tailURL.contains("city="));
            MobileAssert.assertTrue("Incorrect URL when identifier = WeChat Profile, now is: " + getUrl, tailURL.contains("country="));
            MobileAssert.assertTrue("Incorrect URL when identifier = WeChat Profile, now is: " + getUrl, tailURL.contains("unionid="));
        }
    }

    public void verifyURL_service_external(String contentURL, String identifier) {
        String getUrl = getOpenedURL();

        //WebAssert.assertTrue("Failed to open specified URL " + contentURL, getUrl.contains("http://cn.bing.com/"));
        String tailURL = getUrl.substring(getUrl.indexOf(".com/") + 5).replaceAll("\r|\n", "");

        if (identifier.equalsIgnoreCase("None")) {
            MobileAssert.assertTrue("Incorrect URL when identifier = None, now is: " + getUrl, tailURL.equalsIgnoreCase(""));
        } else if (identifier.equalsIgnoreCase("Eloqua ID")) {
            MobileAssert.assertTrue("Incorrect URL when identifier = Eloqua ID, now is: " + getUrl, tailURL.length() > 0);
        } else if (identifier.equalsIgnoreCase("WeChat ID")) {
            MobileAssert.assertTrue("Incorrect URL when identifier = WeChat ID, now is: " + getUrl, tailURL.contains("openid="));
            MobileAssert.assertTrue("Incorrect URL when identifier = WeChat ID, now is: " + getUrl, tailURL.contains("unionid="));
        } else if (identifier.equalsIgnoreCase("WeChat Profile")) {
            MobileAssert.assertTrue("Incorrect URL when identifier = WeChat Profile, now is: " + getUrl, tailURL.contains("openid="));
            MobileAssert.assertTrue("Incorrect URL when identifier = WeChat Profile, now is: " + getUrl, tailURL.contains("nickname="));
            MobileAssert.assertTrue("Incorrect URL when identifier = WeChat Profile, now is: " + getUrl, tailURL.contains("province="));
            MobileAssert.assertTrue("Incorrect URL when identifier = WeChat Profile, now is: " + getUrl, tailURL.contains("city="));
            MobileAssert.assertTrue("Incorrect URL when identifier = WeChat Profile, now is: " + getUrl, tailURL.contains("country="));
            MobileAssert.assertTrue("Incorrect URL when identifier = WeChat Profile, now is: " + getUrl, tailURL.contains("unionid="));
        }
    }

    public void verifyURL_subscription_send_by_campaign(String contentURL) {
        String getUrl = getOpenedURL();
        String tailURL = getUrl.substring(getUrl.indexOf(".com/") + 5);
        MobileAssert.assertTrue("Failed to open specified URL: " + contentURL, getUrl.contains(contentURL));
        MobileAssert.assertTrue("Incorrect URL when identifier = None, now is: " + getUrl, getUrl.contains("CampaignId="));
        MobileAssert.assertFalse("No openid in the opened URL: " + getUrl, getUrl.contains("openid="));
        MobileAssert.assertFalse("No unionid in the opened URL: " + getUrl, getUrl.contains("unionid="));

    }

    public void verifyURL_subscription(String contentURL) {
        String getUrl = getOpenedURL();
        MobileAssert.assertTrue("Failed to open specified URL: " + contentURL, getUrl.contains(contentURL));

        if (contentURL.contains("bing")) {
            MobileAssert.assertFalse("Current URL is: " + getUrl, getUrl.contains("openid="));
            MobileAssert.assertFalse("Current URL is: " + getUrl, getUrl.contains("unionid="));
        } else {
            MobileAssert.assertTrue("No openid in the opened URL: " + getUrl, getUrl.contains("openid="));
            MobileAssert.assertTrue("No unionid in the opened URL: " + getUrl, getUrl.contains("unionid="));
        }
    }

    public void verifyURLNoCopyLink() {
        waitForShort(10);
        getMoreBtn().click();
        waitForShort(2);
        int i = 0;
        while (getCopyLinkBtn().isVisible() && i<3){
            waitForShort(60);
            i++;
            getMoreBtn().click();
            waitForShort(2);
            getMoreBtn().click();
            waitForShort(2);
        }
        MobileAssert.assertFalse("No copy link appears", getCopyLinkBtn().isVisible());
    }

    public void verifyURL_subscription_text_track_normal_service() {
        String getUrl = getOpenedURL();
        MobileAssert.assertTrue(getUrl.contains("openid="));
        MobileAssert.assertTrue(getUrl.contains("unionid="));
    }

    public void verifyURL_subscription_text_track_eloqua_service() {
        String getUrl = getOpenedURL();

        MobileAssert.assertTrue(getUrl.contains("openid="));
        MobileAssert.assertTrue(getUrl.contains("unionid="));
    }

    public void verifyURL_text_track_normal_service() {
        String getUrl = getOpenedURL();

        MobileAssert.assertTrue(getUrl.contains("openid="));
        MobileAssert.assertTrue(getUrl.contains("nickname="));
        MobileAssert.assertTrue(getUrl.contains("province="));
        MobileAssert.assertTrue(getUrl.contains("city="));
        MobileAssert.assertTrue(getUrl.contains("country="));
        MobileAssert.assertTrue(getUrl.contains("unionid="));
    }

    public void verifyURL_text_track_eloqua_service() {
        String getUrl = getOpenedURL();

        MobileAssert.assertTrue(getUrl.contains("openid="));
        MobileAssert.assertTrue(getUrl.contains("nickname="));
        MobileAssert.assertTrue(getUrl.contains("province="));
        MobileAssert.assertTrue(getUrl.contains("city="));
        MobileAssert.assertTrue(getUrl.contains("country="));
        MobileAssert.assertTrue(getUrl.contains("unionid="));
    }


    public String getOpenedURL() {
        waitForShort(3);
        openClipper();
        getMoreBtn().click();
        waitForShort(15);
        getCopyLinkBtn().click();

        String copiedURL = getCopiedURL();
        return copiedURL;
    }

    public boolean isURLCorrect(String contentURL, String identifier) {
        waitForShort(15);
        getMoreBtn().click();
        getCopyLinkBtn().click();

        String copiedURL = getCopiedURL();
        String tailURL = copiedURL.substring(copiedURL.indexOf(".com/") + 5).replace("\r\r\n", "");

        if (identifier.equalsIgnoreCase("None")) {
            MobileAssert.assertTrue("Incorrect URL when identifier = None", tailURL.equalsIgnoreCase(""));
        } else if (identifier.equalsIgnoreCase("WeChat ID")) {
            MobileAssert.assertTrue("Incorrect URL when identifier = WeChat ID", tailURL.contains("openid="));
        } else if (identifier.equalsIgnoreCase("Eloqua ID")) {
            MobileAssert.assertTrue("Incorrect URL when identifier = Eloqua ID", tailURL.length() > 0);
        } else if (identifier.equalsIgnoreCase("WeChat Profile")) {
            MobileAssert.assertTrue("Incorrect URL when identifier = WeChat Profile", tailURL.contains("nickname="));
        }

        return true;
    }

    @PostConstruct
    public void init() {
        //registerComp(moreBtn);
        injectMobileDriverToChildComponents(this);
        waitForScreenReady();
    }

    public void openClipper() {
        Runtime rt = Runtime.getRuntime();
        try {
            Process pr = rt.exec("adb -s " + MobileDriverFactory.instance().getDeviceName() + " shell am startservice ca.zgrs.clipper/.ClipboardService");
            pr.waitFor();
        } catch (Exception e) {
            throw new MobileExceptionBase(getMobileDriverEx(), "Failed to get URL from clipboard.");
        }

    }

    public String getCopiedURL() {
        Runtime rt = Runtime.getRuntime();
        StringBuilder valBuilder;
        try {
            Process pr = rt.exec("adb -s " + MobileDriverFactory.instance().getDeviceName() + " shell am broadcast -a clipper.get");
            pr.waitFor();
            InputStream is = pr.getInputStream();
            Scanner scanner = new Scanner(is).useDelimiter("\\A");
            valBuilder = new StringBuilder();
            while (scanner.hasNext())
                valBuilder.append(scanner.next());
        } catch (Exception e) {
            throw new MobileExceptionBase(getMobileDriverEx(), "Failed to get URL from clipboard.");
        }
        String result = valBuilder.toString();
        return result.substring(result.indexOf("data=") + 5).replace("\"", "");
    }

    public void waitForShort(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}