package com.oracle.auto.testapp.mobile.screens.wechat;

import com.oracle.auto.mobile.assertions.MobileAssert;
import com.oracle.auto.mobile.components.elements.nativeview.NativeButton;
import com.oracle.auto.mobile.components.elements.nativeview.NativeTextField;
import com.oracle.auto.mobile.driver.MobileDriverEx;
import com.oracle.auto.mobile.exceptions.MobileExceptionBase;
import com.oracle.auto.testapp.mobile.ancestor.MobileNativeScreenBase;
import com.oracle.auto.web.utility.WebAssert;
import com.sun.jna.Native;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

/**
 * Created by stepzhou on 7/25/2017.
 */
public class OraAccountScreen extends MobileNativeScreenBase {
    private static final String SEARCHCONTENT = "<p>click here to sign up.</p>";
    private static final int ROUNDS = 6;
    private static final int XOFFSET = 183;
    private static final int YOFFSET = 42;

    private static final String TENCENT_CONTEXT = "WEBVIEW_com.tencent.mm:tools";
    //private static final String TENCENT_CONTEXT = " WEBVIEW_com.tencent.mm:appbrand0";


    private NativeButton messageBtn;
    private NativeTextField messageField;
    private NativeButton sendBtn;

    private NativeButton richMessage;

    public void setRichMessage(NativeButton richMessage) {
        this.richMessage = richMessage;
    }

    public NativeButton getRichMessage() {
        return richMessage;
    }

    public NativeButton getRichMessage(String text) {
        richMessage.setLocator(String.format(richMessage.getLocator(), text));
        int index = findMessageIndex(richMessage.getLocator());
        richMessage = new NativeButton(richMessage.getLocator(), index);
        return richMessage;
    }

    public void setSendBtn(NativeButton sendBtn) {
        this.sendBtn = sendBtn;
    }

    public NativeButton getSendBtn() {
        return sendBtn;
    }

    public NativeButton getSendBtn(String text) {
        sendBtn.setLocator(String.format(sendBtn.getLocator(), text));
        return sendBtn;
    }

    public void setMessageField(NativeTextField messageField) {
        this.messageField = messageField;
    }

    public NativeTextField getMessageField() {
        return messageField;
    }

    public NativeTextField getMessageField(String text) {
        messageField.setLocator(String.format(messageField.getLocator(), text));
        return messageField;
    }

    public void setMessageBtn(NativeButton messageBtn) {
        this.messageBtn = messageBtn;
    }

    public NativeButton getMessageBtn() {
        return messageBtn;
    }

    public NativeButton getMessageBtn(String text) {
        messageBtn.setLocator(String.format(messageBtn.getLocator(), text));
        return messageBtn;
    }

    public void clickHere(String content) {
        int[] coordinates = findCoordinates(content);
        assert coordinates != null;
        getMobileDriverEx().getDriver().tap(1, coordinates[0], coordinates[1], 0);
    }

    public void clickHere() {
        int[] coordinates = findCoordinates();
        assert coordinates != null;
        getMobileDriverEx().getDriver().tap(1, coordinates[0], coordinates[1], 0);
    }

    @PostConstruct
    public void init() {
        injectMobileDriverToChildComponents(this);
        waitForScreenReady();
    }

    private int findMessageIndex(String locator) {
        AppiumDriver driver = getMobileDriverEx().getDriver();
        List elements = driver.findElementsByXPath(locator.substring(6));
        return elements.size() - 1;
    }

    private int[] findCoordinates(String content) {
        AppiumDriver driver = getMobileDriverEx().getDriver();
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;

        int runTimes = 0;
        while (runTimes < ROUNDS) {
            List eleList = driver.findElements(By.id("com.tencent.mm:id/ij"));
            int requireMessageIndex = findRequiredMessage(eleList, content);
            if (requireMessageIndex >= 0) {
                MobileElement mobileElement = (MobileElement) eleList.get(requireMessageIndex);
                if (mobileElement.getLocation().getY() >= 0) {
                    int[] coordinates = new int[2];
                    coordinates[0] = mobileElement.getLocation().getX() + XOFFSET;
                    coordinates[1] = mobileElement.getLocation().getY() + YOFFSET;
                    return coordinates;
                }
            }
            driver.swipe(width / 2, height / 4, width / 2, height * 3 / 4, 1000);
            runTimes++;
        }

        throw new MobileExceptionBase(getMobileDriverEx(), "Failed to find the specified Clickable element.");
    }

    private int[] findCoordinates() {
        AppiumDriver driver = getMobileDriverEx().getDriver();
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;

        int runTimes = 0;
        while (runTimes < ROUNDS) {
            List eleList = driver.findElements(By.id("com.tencent.mm:id/ij"));
            int requireMessageIndex = findRequiredMessage(eleList, SEARCHCONTENT);
            if (requireMessageIndex >= 0) {
                MobileElement mobileElement = (MobileElement) eleList.get(requireMessageIndex);
                if (mobileElement.getLocation().getY() >= 0) {
                    int[] coordinates = new int[2];
                    coordinates[0] = mobileElement.getLocation().getX() + XOFFSET;
                    coordinates[1] = mobileElement.getLocation().getY() + YOFFSET;
                    return coordinates;
                }
            }
            driver.swipe(width / 2, height / 4, width / 2, height * 3 / 4, 1000);
            runTimes++;
        }

        throw new MobileExceptionBase(getMobileDriverEx(), "Failed to find the specified Clickable element.");
    }

    public void findContentsAndClick(String text) {
        NativeButton findElement = findContents(text);
        findElement.click();
    }

    public boolean verifyIfContentsNotFound(String text) {
        //  boolean textStatus = verifyIfFindContents(text);
        return verifyIfFindContents(text, 3);
    }


    public String findContentsInElements(String text) {
        NativeTextField findElement = findContentsInElement(text);
        return findElement.getValue();
    }

    public void verifyURL_service_external(String contentURL, String identifier) {
        String getUrl = getOpenedURL();

        WebAssert.assertTrue("Failed to open specified URL " + contentURL, getUrl.contains("http://cn.bing.com/"));
        String tailURL = getUrl.substring(getUrl.indexOf(".com/") + 5);

        if (identifier.equalsIgnoreCase("None")) {
            MobileAssert.assertTrue("Incorrect address when identifier = None", tailURL.equalsIgnoreCase(""));
        } else if (identifier.equalsIgnoreCase("Eloqua ID")) {
            MobileAssert.assertTrue("Incorrect address when identifier = Eloqua ID", tailURL.length() > 0);
        } else if (identifier.equalsIgnoreCase("WeChat ID")) {
            MobileAssert.assertTrue("Incorrect address when identifier = WeChat ID", tailURL.contains("openid="));
            MobileAssert.assertTrue("Incorrect address when identifier = WeChat ID", tailURL.contains("unionid="));
        } else if (identifier.equalsIgnoreCase("WeChat Profile")) {
            MobileAssert.assertTrue("Incorrect address when identifier = WeChat Profile", tailURL.contains("openid="));
            MobileAssert.assertTrue("Incorrect address when identifier = WeChat Profile", tailURL.contains("nickname="));
            MobileAssert.assertTrue("Incorrect address when identifier = WeChat Profile", tailURL.contains("province="));
            MobileAssert.assertTrue("Incorrect address when identifier = WeChat Profile", tailURL.contains("city="));
            MobileAssert.assertTrue("Incorrect address when identifier = WeChat Profile", tailURL.contains("country="));
            MobileAssert.assertTrue("Incorrect address when identifier = WeChat Profile", tailURL.contains("unionid="));
        }
    }

    public void verifyURL_service_internal(String contentURL, String identifier) {
        String getUrl = getOpenedURL();

        MobileAssert.assertTrue("Failed to open specified URL " + contentURL, getUrl.contains("http://p01.msqa01.com/ElQWECHATREADMORE"));
        MobileAssert.assertTrue("No openid in the opened URL...", getUrl.contains("openid="));
        MobileAssert.assertTrue("No nickname in the opened URL...", getUrl.contains("nickname="));
        MobileAssert.assertTrue("No province in the opened URL...", getUrl.contains("province="));
        MobileAssert.assertTrue("No city in the opened URL...", getUrl.contains("city="));
        MobileAssert.assertTrue("No country in the opened URL...", getUrl.contains("country="));
        MobileAssert.assertTrue("No unionid in the opened URL...", getUrl.contains("unionid="));
    }

    public void verifyURL_subscription(String contentURL) {
        String getUrl = getOpenedURL();
        MobileAssert.assertTrue("Failed to open specified URL " + contentURL, getUrl.contains(contentURL));

        if (contentURL.contains("bing")) {
            MobileAssert.assertFalse(getUrl.contains("openid="));
            MobileAssert.assertFalse(getUrl.contains("unionid="));
        } else if (contentURL.contains("msqa01")) {
            MobileAssert.assertTrue("No openid in the opened URL...", getUrl.contains("openid="));
            MobileAssert.assertTrue("No unionid in the opened URL...", getUrl.contains("unionid="));
        }
    }

    public void verifyURL_text_untrack() {
        String getUrl = getOpenedURL();

        MobileAssert.assertFalse(getUrl.contains("openid="));
        MobileAssert.assertFalse(getUrl.contains("nickname="));
        MobileAssert.assertFalse(getUrl.contains("province="));
        MobileAssert.assertFalse(getUrl.contains("city="));
        MobileAssert.assertFalse(getUrl.contains("country="));
        MobileAssert.assertFalse(getUrl.contains("unionid="));
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

    public void verifyURL_subscription_text_track_eloqua_service() {
        String getUrl = getOpenedURL();

        MobileAssert.assertTrue(getUrl.contains("openid="));
        MobileAssert.assertTrue(getUrl.contains("unionid="));
    }

    public void verifyURL_subscription_text_track_normal_service() {
        String getUrl = getOpenedURL();

        MobileAssert.assertTrue(getUrl.contains("openid="));
        MobileAssert.assertTrue(getUrl.contains("unionid="));
    }

    private String getOpenedURL() {
        MobileDriverEx.waitFor(10);

        AppiumDriver driver = getMobileDriverEx().getDriver();
        Set<String> test = driver.getContextHandles();
        try {
            driver.context(TENCENT_CONTEXT);
        } catch (Exception ex) {
            throw new MobileExceptionBase(getMobileDriverEx(), "Failed to switch to WebView.");
        }
        return driver.getCurrentUrl();
    }

    public boolean findLatestContent(String content, int index) {
        String xPathLocator = String.format("xpath=//android.widget.TextView[contains(@text, '%s')]", content);
        NativeButton textContent = new NativeButton(xPathLocator, index);
        textContent.setMobileDriverEx(getMobileDriverEx());

        return textContent.isPresent();
    }

    public void closeWebSite() {
        String locator = "xpath=//android.widget.ImageView[@content-desc='返回']";
        NativeButton back = new NativeButton(locator);
        back.setMobileDriverEx(getMobileDriverEx());
        back.click();

        MobileDriverEx.waitFor(2);
        AppiumDriver driver = getMobileDriverEx().getDriver();
        System.out.println(driver.getContext());
    }

    private NativeButton findContents(String text) {
        MobileDriverEx.waitFor(5);
        String locator = String.format("xpath=//android.widget.TextView[contains(@text, '%s')]", text);
        NativeButton article = new NativeButton(locator);
        article.setMobileDriverEx(getMobileDriverEx());

        AppiumDriver driver = getMobileDriverEx().getDriver();
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;

        int runTimes = 0;
        //Firstly swipe to the bottom of screen
        while (runTimes < ROUNDS) {
            MobileDriverEx.waitFor(2);

            if (article.isPresent())
                return article;

            //roll up
            driver.swipe(width / 2, height / 2, width / 2, height / 7, 1000);
            runTimes++;
        }

        runTimes = 0;
        while (runTimes < ROUNDS) {
            MobileDriverEx.waitFor(2);

            if (article.isPresent())
                return article;
            //roll down
            driver.swipe(width / 2, height / 4, width / 2, height * 3 / 4, 1000);
            runTimes++;
        }

        throw new MobileExceptionBase(getMobileDriverEx(), "Failed to find the element: " + text);
    }

    private boolean verifyIfFindContents(String text, int minutes) {
        String locator = String.format("xpath=//android.widget.TextView[contains(@text, '%s')]", text);
        NativeButton article = new NativeButton(locator);
        article.setMobileDriverEx(getMobileDriverEx());

        AppiumDriver driver = getMobileDriverEx().getDriver();
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;

        int runTimes = 0;
        while (runTimes < ROUNDS * minutes) {
            MobileDriverEx.waitFor(10);

            if (article.isPresent())
                return true;

            driver.swipe(width / 2, height / 4, width / 2, height * 3 / 4, 1000);
            runTimes++;
        }
        return false;
        //  throw new MobileExceptionBase(getMobileDriverEx(), "Failed to find the element: " + text);
    }

    private NativeTextField findContentsInElement(String text) {
        String locator = String.format("xpath=//android.widget.TextView[contains(@text, '%s')]", text);
        NativeTextField article = new NativeTextField(locator);
        article.setMobileDriverEx(getMobileDriverEx());

        AppiumDriver driver = getMobileDriverEx().getDriver();
        int width = driver.manage().window().getSize().width;
        int height = driver.manage().window().getSize().height;

        int runTimes = 0;
        while (runTimes < ROUNDS) {
            MobileDriverEx.waitFor(2);

            if (article.isPresent())
                return article;

            driver.swipe(width / 2, height / 4, width / 2, height * 3 / 4, 1000);
            runTimes++;
        }

        throw new MobileExceptionBase(getMobileDriverEx(), "Failed to find the element: " + text);
    }

    private int findRequiredMessage(List messages, final String contents) {
        for (int i = 0; i < messages.size(); i++) {
            if (((MobileElement) messages.get(i)).getText().contains(contents))
                return i;
        }
        return -1;
    }

    public void verifyContentsInElements(String exceptedContent, String ActualContent) {
        MobileAssert.assertEquals("Failed to get excepted content" + exceptedContent, exceptedContent, ActualContent);
    }

    public void checkContentsInElements(String exceptedContent, String ActualContent) {
        MobileAssert.assertContains("Failed to get excepted content" + exceptedContent, exceptedContent, ActualContent);
    }

}