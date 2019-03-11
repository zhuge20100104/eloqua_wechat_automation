package com.oracle.auto.testapp.web.pages.MenuServicePages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import com.oracle.auto.web.comp.html.HTMLInput;
import com.oracle.auto.web.comp.html.HTMLTextArea;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by grcao on 8/08/2017.
 */

@Component
public class TemplateMessageConPage extends TestAppPageBase {

    private HTMLDiv templateBox;
    private HTMLDiv templateBoxArrow;
    private HTMLTextArea templateSearchBox;
    private HTMLDiv listedTemplates;
    private HTMLDiv exceptedTemplate;

    private HTMLTextArea templateName;
    private HTMLTextArea contentUpper;
    private HTMLTextArea templateLink;
    private HTMLDiv identifierBtn;
    private HTMLDiv exceptedIdentifier;

    private HTMLTextArea first;
    private HTMLTextArea theme;
    private HTMLTextArea time;
    private HTMLTextArea result;
    private HTMLTextArea contentBottom;
    private HTMLTextArea appID;
    private HTMLTextArea pagePath;

    private HTMLDiv firstColorBtn;
    private HTMLDiv themeColorBtn;
    private HTMLDiv timeColorBtn;
    private HTMLDiv resultColorBtn;

    private HTMLDiv colorRed;
    private HTMLDiv colorBlue;
    private HTMLDiv colorOrange;

    private HTMLTextArea time2;
    private HTMLTextArea ip;
    private HTMLTextArea content2Bottom;

    private HTMLDiv previewBtn;
    private HTMLDiv QRCode;
    private HTMLDiv closeQRCode;
    private HTMLDiv saveBtn;


    @PostConstruct
    public void init() {
        injectPageToChildComponents(this);
        waitForPageReady();
    }

    public void setTemplateBox(HTMLDiv templateBox) {
        this.templateBox = templateBox;
    }

    public HTMLDiv getTemplateBox() {
        return templateBox;
    }

    public void setTemplateBoxArrow(HTMLDiv templateBoxArrow) {
        this.templateBoxArrow = templateBoxArrow;
    }

    public HTMLDiv getTemplateBoxArrow() {
        return templateBoxArrow;
    }

    public void setTemplateSearchBox(HTMLTextArea templateSearchBox) {
        this.templateSearchBox = templateSearchBox;
    }

    public HTMLTextArea getTemplateSearchBox() {
        return templateSearchBox;
    }

    public void setListedTemplates(HTMLDiv listedTemplates) {
        this.listedTemplates = listedTemplates;
    }

    public HTMLDiv getListedTemplates() {
        return listedTemplates;
    }

    public void setExceptedTemplate(HTMLDiv exceptedTemplate) {
        this.exceptedTemplate = exceptedTemplate;
    }

    public HTMLDiv getExceptedTemplate() {
        return exceptedTemplate;
    }


    public int getTemplateIndex(String value) {
        List<String> accounts = getListsTemplates();
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).contains(value))
                return i;
        }
        return -1;
    }

    public List<String> getListsTemplates() {
        List<String> templateList = new ArrayList<>();
        int numbers = getListedTemplates().getMethodProp(".size()").AsInt();
        for (int i = 0; i < numbers; i++) {
            templateList.add(getExceptedTemplate(i).getText());
        }
        return templateList;
    }

    public HTMLDiv getExceptedTemplate(int index) {
        exceptedTemplate.setLocator(String.format(exceptedTemplate.getRawLocator(), index));
        return exceptedTemplate;
    }


    public void setTemplateName(HTMLTextArea templateName) {
        this.templateName = templateName;
    }

    public HTMLTextArea getTemplateName() {
        return templateName;
    }


    public void setContentUpper(HTMLTextArea contentUpper) {
        this.contentUpper = contentUpper;
    }

    public HTMLTextArea getContentUpper() {
        return contentUpper;
    }

    public void setContentBottom(HTMLTextArea contentBottom) {
        this.contentBottom = contentBottom;
    }

    public HTMLTextArea getContentBottom() {
        return contentBottom;
    }

    public HTMLTextArea getAppID() {
        return appID;
    }

    public void setAppID(HTMLTextArea appID) {
        this.appID = appID;
    }

    public HTMLTextArea getPagePath() {
        return pagePath;
    }

    public void setPagePath(HTMLTextArea pagePath) {
        this.pagePath = pagePath;
    }

    public void setContent2Bottom(HTMLTextArea content2Bottom) {
        this.content2Bottom = content2Bottom;
    }

    public HTMLTextArea getContent2Bottom() {
        return content2Bottom;
    }


    public void setTemplateLink(HTMLTextArea templateLink) {

        this.templateLink = templateLink;
    }

    public HTMLTextArea getTemplateLink() {
        return templateLink;
    }

    public void setIdentifierBtn(HTMLDiv identifierBtn) {
        this.identifierBtn = identifierBtn;
    }

    public HTMLDiv getIdentifierBtn() {
        return identifierBtn;
    }

    public void setExceptedIdentifier(HTMLDiv exceptedIdentifier) {
        this.exceptedIdentifier = exceptedIdentifier;
    }

    public HTMLDiv getExceptedIdentifier() {
        return exceptedIdentifier;
    }

    //Get excepted identifer item
    public HTMLDiv getExceptedIdentifier(int index) {
        exceptedIdentifier.setLocator(String.format(exceptedIdentifier.getRawLocator(), index));
        return exceptedIdentifier;
    }

    public int getIdentifierIndex(String identifierName) {
        switch (identifierName) {
            case "None":
            case "无":
                return 4;
            case "Eloqua ID":
                return 1;
            case "WeChat ID":
            case "微信 ID":
                return 2;
            case "WeChat Profile":
            case "微信信息":
                return 3;
            default:
                return -1;

        }
    }

//    public HTMLInput getAPPID(){
//        HTMLInput appID = new HTMLInput("id=template-mini-input");
//        appID.setPage(page());
//        return appID;
//    }
//
//    public HTMLInput getPagePath(){
//        HTMLInput pagePath = new HTMLInput("id=template-mini-input");
//        pagePath.setPage(page());
//        return pagePath;
//    }
    public HTMLTextArea getFirst() {
        return first;
    }

    public void setFirst(HTMLTextArea first) {
        this.first = first;
    }

    public void setTheme(HTMLTextArea theme) {
        this.theme = theme;
    }

    public HTMLTextArea getTheme() {
        return theme;
    }

    public void setTime(HTMLTextArea time) {
        this.time = time;
    }

    public HTMLTextArea getTime() {
        return time;
    }

    public void setResult(HTMLTextArea result) {
        this.result = result;
    }

    public HTMLTextArea getResult() {
        return result;
    }

    public HTMLDiv getFirstColorBtn() {
        return firstColorBtn;
    }

    public void setFirstColorBtn(HTMLDiv firstColorBtn) {
        this.firstColorBtn = firstColorBtn;
    }

    public HTMLDiv getThemeColorBtn() {
        return themeColorBtn;
    }

    public void setThemeColorBtn(HTMLDiv themeColorBtn) {
        this.themeColorBtn = themeColorBtn;
    }

    public HTMLDiv getTimeColorBtn() {
        return timeColorBtn;
    }

    public void setTimeColorBtn(HTMLDiv timeColorBtn) {
        this.timeColorBtn = timeColorBtn;
    }

    public HTMLDiv getResultColorBtn() {
        return resultColorBtn;
    }

    public void setResultColorBtn(HTMLDiv resultColorBtn) {
        this.resultColorBtn = resultColorBtn;
    }

//    public HTMLDiv getColorRed(){
//        HTMLDiv ele = new HTMLDiv("BDD_jFindByAttr(\"div\",\"style\",\"background-color:rgb(255, 38, 0)\")[0]");
//       // return new HTMLDiv("BDD_jFindByAttr(\"div\",\"style\",\"background-color:rgb(255, 38, 0)\")[0]");
//        return ele;
//    }

    public HTMLDiv getColorRed() {
        return colorRed;
    }

    public void setColorRed(HTMLDiv colorRed) {
        this.colorRed = colorRed;
    }

    public HTMLDiv getColorBlue() {
        return colorBlue;
    }

    public void setColorBlue(HTMLDiv colorBlue) {
        this.colorBlue = colorBlue;
    }

    public HTMLDiv getColorOrange() {
        return colorOrange;
    }

    public void setColorOrange(HTMLDiv colorOrange) {
        this.colorOrange = colorOrange;
    }

    public HTMLDiv getColorRed = new HTMLDiv("BDD_jFindByAttr(\"div\",\"style\",\"background-color:rgb(255, 38, 0)\")[0]");

    public void setTime2(HTMLTextArea time2) {
        this.time2 = time2;
    }

    public HTMLTextArea getTime2() {
        return time2;
    }

    public void setIp(HTMLTextArea ip) {
        this.ip = ip;
    }

    public HTMLTextArea getIp() {
        return ip;
    }

    public void setPreviewBtn(HTMLDiv previewBtn) {
        this.previewBtn = previewBtn;
    }

    public HTMLDiv getPreviewBtn() {
        return previewBtn;
    }

    public void setQRCode(HTMLDiv qrCode) {
        this.QRCode = qrCode;
    }

    public HTMLDiv getQRCode() {
        return QRCode;
    }

    public void setCloseQRCode(HTMLDiv closeQRCode) {
        this.closeQRCode = closeQRCode;
    }

    public HTMLDiv getCloseQRCode() {
        return closeQRCode;
    }

    public void setSaveBtn(HTMLDiv saveBtn) {
        this.saveBtn = saveBtn;
    }

    public HTMLDiv getSaveBtn() {
        return saveBtn;
    }

}
