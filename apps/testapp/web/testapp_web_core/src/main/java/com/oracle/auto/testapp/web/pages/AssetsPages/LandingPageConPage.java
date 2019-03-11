package com.oracle.auto.testapp.web.pages.AssetsPages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLComponentBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import com.oracle.auto.web.comp.html.HTMLInput;
import com.oracle.auto.web.utility.PropertyConfiger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by grace on 8/29/2017.
 */

@Component
public class LandingPageConPage extends TestAppPageBase {

    private HTMLDiv cloudContact;
    private HTMLDiv form;

    //cloud content box
    private HTMLInput searchBox;
    private HTMLDiv listedIssues;
    private HTMLDiv exceptedIssues;
    private HTMLDiv closeCloudContentBtn;

    private HTMLDiv domainArrow;
    private HTMLDiv cloudContentRegistrationIssue;
    private HTMLDiv cloudContentJSIssue;
    private HTMLDiv formIssue;

    private HTMLInput domainInputBox;
    private HTMLDiv exceptedDomain;
    private HTMLDiv domainLists;
    private HTMLInput vanityURL;
    private HTMLDiv contentURL;
    private HTMLDiv landingPageURL;

    private HTMLDiv drawingArea;

    //click save btn then input name in pop box
    private HTMLDiv saveLPBtn;
    private HTMLInput inputLPName;
    private HTMLDiv saveLPNameBtn;


    private HTMLDiv serviceOpenID;

    public HTMLDiv getServiceOpenID() {
        return serviceOpenID;
    }

    public void setServiceOpenID(HTMLDiv serviceOpenID) {
        this.serviceOpenID = serviceOpenID;
    }

    @PostConstruct
    public void init() {
        registerComp(cloudContact);
        injectPageToChildComponents(this);
        waitForPageReady();
    }

    public void setCloudContact(HTMLDiv cloudContact) {
        this.cloudContact = cloudContact;
    }

    public HTMLDiv getCloudContact() {
        return cloudContact;
    }

    public HTMLDiv getForm() {
        return form;
    }

    public void setForm(HTMLDiv form) {
        this.form = form;
    }

    public void setSearchBox(HTMLInput searchBox) {
        this.searchBox = searchBox;
    }

    public HTMLInput getSearchBox() {
        return searchBox;
    }

    public void setListedIssues(HTMLDiv listedIssues) {
        this.listedIssues = listedIssues;
    }

    public HTMLDiv getListedIssues() {
        return listedIssues;
    }

    public void setExceptedIssues(HTMLDiv exceptedIssues) {
        this.exceptedIssues = exceptedIssues;
    }

    public HTMLDiv getExceptedIssues() {
        return exceptedIssues;
    }

    public HTMLDiv getCloseCloudContentBtn() {
        return closeCloudContentBtn;
    }

    public void setCloseCloudContentBtn(HTMLDiv closeCloudContentBtn) {
        this.closeCloudContentBtn = closeCloudContentBtn;
    }

    public int getExceptedIssueIndex(String value) {
        List<String> items = getListsIssuesList();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).contains(value))
                return i;
        }
        return -1;
    }

    public List<String> getListsIssuesList() {
        List<String> itemList = new ArrayList<>();
        int numbers = getListedIssues().getMethodProp(".size()").AsInt();
        for (int i = 0; i < numbers; i++) {
            itemList.add(getExceptedCloudContentIssueIndex(i).getText());
        }
        return itemList;
    }

    public HTMLDiv getExceptedCloudContentIssueIndex(int index) {
        exceptedIssues.setLocator(String.format(exceptedIssues.getRawLocator(), index));
        return exceptedIssues;
    }

    public void dragElement(int index) {
        getExceptedIssues().dragAndDropTo(getExceptedCloudContentIssueIndex(index), 720, 720);
    }

    public HTMLDiv getDomainArrow() {
        return domainArrow;
    }

    public void setDomainArrow(HTMLDiv domainArrow) {
        this.domainArrow = domainArrow;
    }

    public HTMLDiv getCloudContentRegistrationIssue() {
        return cloudContentRegistrationIssue;
    }

    public void setCloudContentRegistrationIssue(HTMLDiv cloudContentRegistrationIssue) {
        this.cloudContentRegistrationIssue = cloudContentRegistrationIssue;
    }

    public HTMLDiv getFormIssue() {
        return formIssue;
    }

    public void setFormIssue(HTMLDiv formIssue) {
        this.formIssue = formIssue;
    }
    public HTMLDiv getCloudContentJSIssue() {
        return cloudContentJSIssue;
    }

    public void setCloudContentJSIssue(HTMLDiv cloudContentJSIssue) {
        this.cloudContentJSIssue = cloudContentJSIssue;
    }

    public void dragElement() {
        getCloudContentJSIssue().dragAndDropTo(getCloudContentJSIssue(), 720, 720);
    }


    public HTMLInput getDomainInputBox() {
        return domainInputBox;
    }

    public void setDomainInputBox(HTMLInput domainInputBox) {
        this.domainInputBox = domainInputBox;
    }

    public HTMLDiv getExceptedDomain() {
        return exceptedDomain;
    }

    public void setExceptedDomain(HTMLDiv exceptedDomain) {
        this.exceptedDomain = exceptedDomain;
    }

    public HTMLDiv getDomainLists() {
        return domainLists;
    }

    public void setDomainLists(HTMLDiv domainLists) {
        this.domainLists = domainLists;
    }

    public HTMLInput getVanityURL() {
        return vanityURL;
    }

    public void setVanityURL(HTMLInput vanityURL) {
        this.vanityURL = vanityURL;
    }

    public HTMLDiv getContentURL() {
        return contentURL;
    }

    public void setContentURL(HTMLDiv contentURL) {
        this.contentURL = contentURL;
    }

    public HTMLDiv getLandingPageURL() {
        return landingPageURL;
    }

    public void setLandingPageURL(HTMLDiv landingPageURL) {
        this.landingPageURL = landingPageURL;
    }

    public void setDrawingArea(HTMLDiv drawingArea) {
        this.drawingArea = drawingArea;
    }

    public HTMLDiv getDrawingArea() {
        return drawingArea;
    }

//    public void dragSourceToTarget(HTMLComponentBase sour, HTMLComponentBase target){
//       //new Actions(page.getDriver()).dragAndDrop(sour,target).build().perform();
//      //  new Actions(page.getDriver()).dragAndDropBy(srcElement, srcElement.getLocation().x, 50).build().perform();
//       // page.waitForShort();
//        getExceptedIssues().dragAndDropToTarget(s);
//    }

    public void setSaveLPBtn(HTMLDiv saveLPBtn) {
        this.saveLPBtn = saveLPBtn;
    }

    public HTMLDiv getSaveLPBtn() {
        return saveLPBtn;
    }

    public void setInputLPName(HTMLInput inputLPName) {
        this.inputLPName = inputLPName;
    }

    public HTMLInput getInputLPName() {
        return inputLPName;
    }

    public void setSaveLPNameBtn(HTMLDiv saveLPNameBtn) {
        this.saveLPNameBtn = saveLPNameBtn;
    }

    public HTMLDiv getSaveLPNameBtn() {
        return saveLPNameBtn;
    }

    public void dragOpenLandingPage() {
        WebDriver driver = page.getDriver();
        driver.manage().window().setSize(new Dimension(1296, 696));

        WebElement cloudContentContainer = driver.findElements(By.className("title-bar")).get(0);
        Actions builder0 = new Actions(driver);
        builder0.dragAndDropBy(cloudContentContainer, -120, -5).build().perform();
        //builder0.dragAndDropBy(cloudContentContainer, -200, -5).build().perform();

        WebElement cloudCompItem = driver.findElements(By.className("cloud-component-item")).get(0);
        Actions builder = new Actions(driver);
        //builder.clickAndHold(cloudCompItem).moveByOffset(0, -100).build().perform();
        builder.clickAndHold(cloudCompItem).moveByOffset(-100, -250).build().perform();
        page.waitForSecond(2);
        builder.clickAndHold(cloudCompItem).release(cloudCompItem).build().perform();
        page.waitForSecond(5);

        builder0.dragAndDropBy(cloudContentContainer, -100, -150).build().perform();
        driver.manage().window().maximize();
        page.waitForSecond(5);
    }

    public void dragOneForm(String formName) {
        WebDriver driver = page.getDriver();
        driver.manage().window().setSize(new Dimension(1296, 696));
        WebElement cloudContentContainer = driver.findElements(By.className("title-bar")).get(0);
        Actions builder0 = new Actions(driver);
        builder0.dragAndDropBy(cloudContentContainer, -120, -5).build().perform();
        // builder0.dragAndDropBy(cloudContentContainer, -180, -5).build().perform();

       // WebElement cloudCompItem = driver.findElements(By.className("sc-collection-item")).get(0);
//div[@title='AutoTestRegistration-DoNotDelete']/parent::*
        String xPathLocator = String.format("//div[@title='%s']/parent::*", formName);
        WebElement cloudCompItem = driver.findElement(By.xpath(xPathLocator));
        Actions builder = new Actions(driver);
        builder.clickAndHold(cloudCompItem).moveByOffset(100, -100).build().perform();
        // builder.clickAndHold(cloudCompItem).moveByOffset(100, -200).build().perform();
        // builder.clickAndHold(cloudCompItem).moveByOffset(-100, -300).build().perform();
        page.waitForSecond(2);
        builder.clickAndHold(cloudCompItem).release(cloudCompItem).build().perform();

        builder0.dragAndDropBy(cloudContentContainer, 250, 5).build().perform();
        driver.manage().window().maximize();
        page.waitForSecond(5);

    }

    public void openContent() {
        WebDriver driver = page.getDriver();
        WebElement iFrame = driver.findElement(By.tagName("iframe"));
        driver.switchTo().defaultContent();
        driver.switchTo().frame(iFrame);

        ////driver.manage().window().setSize(new Dimension(1205, 696));
        page.waitForSecond(20);
        WebElement landingPage = driver.findElements(By.className("cloud-content-replaceable")).get(0);
        Actions action = new Actions(page.getDriver());
        action.doubleClick(landingPage).perform();

    }


}
