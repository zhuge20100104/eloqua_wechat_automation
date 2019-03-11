package com.oracle.auto.testapp.web.pages.CampaignPages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import com.oracle.auto.web.comp.html.HTMLTextArea;
import com.oracle.auto.web.comp.html.HTMLValueDisplay;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by grcao on 7/19/2017.
 */

@Component
public class RichMediaMessageConPage extends TestAppPageBase {
    private HTMLDiv mediaNews;
    private HTMLDiv mediaNewsSelected;
    private HTMLDiv submitBtn;
    private HTMLValueDisplay successfulMsg;
    private HTMLDiv closeBtn;

    private HTMLDiv mediaNewsTitleLists;
    private HTMLDiv exceptedMediaNewsTitle;

    public void setSuccessfulMsg(HTMLValueDisplay successfulMsg) {
        this.successfulMsg = successfulMsg;
    }

    public HTMLValueDisplay getSuccessfulMsg() {
        return successfulMsg;
    }

    public void setMediaNewsSelected(HTMLDiv mediaNewsSelected) {
        this.mediaNewsSelected = mediaNewsSelected;
    }

    public HTMLDiv getMediaNewsSelected() {
        return mediaNewsSelected;
    }

    public void setMediaNews(HTMLDiv mediaNews) {
        this.mediaNews = mediaNews;
    }

    public HTMLDiv getMediaNews() {
        return mediaNews;
    }

    public void setSubmitBtn(HTMLDiv submitBtn) {
        this.submitBtn = submitBtn;
    }

    public HTMLDiv getSubmitBtn() {
        return submitBtn;
    }

    public void setCloseBtn(HTMLDiv closeBtn) {
        this.closeBtn = closeBtn;
    }

    public HTMLDiv getCloseBtn() {
        return closeBtn;
    }

    public void setMediaNewsTitleLists(HTMLDiv mediaNewsTitleLists) {
        this.mediaNewsTitleLists = mediaNewsTitleLists;
    }

    public HTMLDiv getMediaNewsTitleLists() {
        return mediaNewsTitleLists;
    }

    public void setExceptedMediaNewsTitle(HTMLDiv exceptedMediaNewsTitle) {
        this.exceptedMediaNewsTitle = exceptedMediaNewsTitle;
    }        //return getAccountLists().indexOf(value);

    public HTMLDiv getExceptedMediaNewsTitle() {
        return exceptedMediaNewsTitle;
    }

    public List<String> getMediaLists() {
        List<String> accountList = new ArrayList<>();
        int numbers = getMediaNewsTitleLists().getMethodProp(".size()").AsInt();
        for (int i = 0; i < numbers; i++) {
            accountList.add(getExceptedMedia(i).getText());
        }
        return accountList;
    }

    public int getNewsMediaIndex(String value) {
        List<String> accounts = getMediaLists();
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).contains(value))
                return i;
        }
        return -1;
    }

    public HTMLDiv getExceptedMedia(int index) {
        exceptedMediaNewsTitle.setLocator(String.format(exceptedMediaNewsTitle.getRawLocator(), index));
        return exceptedMediaNewsTitle;
    }

    @PostConstruct
    public void init() {
        //registerComp(topOptions);
        injectPageToChildComponents(this);
        waitForPageReady();
    }

    public boolean submitResult() {
        WebDriverSeleniumPageEx.ScriptExecuteResult ret = page().executeScriptEx("var comp=document.getElementsByClassName(\"alert-success\")[0];return comp.textContent");
        return ret.str().contains("success");
    }

    public void waitLoadData() {
        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean successfulMsgIsVisibleOrNot() {
        int index = 30*60;
        boolean status = false;
        for (int i = 0; i < index; i++) {
            if (getSuccessfulMsg().isReady()) {

                status = true;
                break;
            } else {
                page().waitMilliSecond();
            }
        }

        return status;
    }

}