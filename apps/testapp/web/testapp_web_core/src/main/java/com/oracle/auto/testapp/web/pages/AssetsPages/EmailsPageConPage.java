package com.oracle.auto.testapp.web.pages.AssetsPages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import com.oracle.auto.web.comp.html.HTMLInput;
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
 * Created by grace on 8/30/2017.
 */

@Component
public class EmailsPageConPage extends TestAppPageBase {

    private HTMLDiv cloudContact;
    private HTMLDiv textBtn;

    private HTMLInput searchBox;
    private HTMLDiv listedIssues;
    private HTMLDiv exceptedIssues;

    private HTMLDiv drawingArea;
    private HTMLDiv saveEmailBtn;

    private HTMLInput inputEmailName;
    private HTMLInput emailSubject;
    private HTMLDiv  savePopupEmailName;

    private HTMLDiv element;
    private HTMLDiv textElement;

    private HTMLDiv emailGroupBtn;
    private HTMLDiv emailGroupLists;
    private HTMLDiv emailGroupName;


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

    public void draganddrop() {
        getExceptedIssues().dragDrop(element, drawingArea);
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

    public void setSaveEmailBtn(HTMLDiv saveEmailBtn) {
        this.saveEmailBtn = saveEmailBtn;
    }

    public HTMLDiv getSaveEmailBtn() {
        return saveEmailBtn;
    }

    public void setInputEmailName(HTMLInput inputEmailName) {
        this.inputEmailName = inputEmailName;
    }

    public HTMLInput getInputEmailName() {
        return inputEmailName;
    }

    public void setEmailSubject(HTMLInput emailSubject) {
        this.emailSubject = emailSubject;
    }

    public HTMLInput getEmailSubject() {
        return emailSubject;
    }

    public void setElement(HTMLDiv element) {
        this.element = element;
    }

    public HTMLDiv getElement() {
        return element;
    }

    public void setTextElement(HTMLDiv textElement) {
        this.textElement = textElement;
    }

    public HTMLDiv getTextElement() {
        return textElement;
    }

    public void setTextBtn(HTMLDiv textBtn) {
        this.textBtn = textBtn;
    }

    public HTMLDiv getTextBtn() {
        return textBtn;
    }

    public HTMLDiv getSavePopupEmailName() {
        return savePopupEmailName;
    }

    public void setSavePopupEmailName(HTMLDiv savePopupEmailName) {
        this.savePopupEmailName = savePopupEmailName;
    }

    public void dragOpenLandingPage() {
        WebDriver driver = page.getDriver();
        driver.manage().window().setSize(new Dimension(1296, 696));

        WebElement cloudContentContainer = driver.findElements(By.className("title-bar")).get(0);
        Actions builder0 = new Actions(driver);
        builder0.dragAndDropBy(cloudContentContainer, -120, -5).build().perform();
        page.waitForSecond(2);

        WebElement cloudCompItem = driver.findElements(By.className("cloud-component-item")).get(0);
        Actions builder = new Actions(driver);
        builder.clickAndHold(cloudCompItem).moveByOffset(0, -100).build().perform();
        page.waitForSecond(2);
        builder.clickAndHold(cloudCompItem).release(cloudCompItem).build().perform();

        page.waitForSecond(2);
        builder0.dragAndDropBy(cloudContentContainer, 120, 5).build().perform();

        WebElement iFrame = driver.findElement(By.tagName("iframe"));
        driver.switchTo().defaultContent();
        driver.switchTo().frame(iFrame);

        driver.manage().window().setSize(new Dimension(1205, 696));

        page.waitForSecond(20);
        WebElement landingPage = driver.findElements(By.className("handles")).get(0);
        Actions action = new Actions(page.getDriver());
        action.doubleClick(landingPage).perform();

        driver.manage().window().maximize();
        page.waitForSecond(2);

    }

    public HTMLDiv getEmailGroupLists() {
        return emailGroupLists;
    }

    public void setEmailGroupLists(HTMLDiv emailGroupLists) {
        this.emailGroupLists = emailGroupLists;
    }

    public HTMLDiv getEmailGroupBtn() {
        return emailGroupBtn;
    }

    public void setEmailGroupBtn(HTMLDiv emailGroupBtn) {
        this.emailGroupBtn = emailGroupBtn;
    }

    public HTMLDiv getEmailGroupName() {
        return emailGroupName;
    }

    public void setEmailGroupName(HTMLDiv emailGroupName) {
        this.emailGroupName = emailGroupName;
    }

    public List<String> getGroupLists() {
        List<String> accountList = new ArrayList<>();
        int numbers = getGroupNumbers();
        for (int i = 0; i < numbers; i++) {
            accountList.add(getEmailGroup(i).getText());
        }
        return accountList;
    }

    private int getGroupNumbers() {
        return getEmailGroupLists().getMethodProp(".size()").AsInt();
    }

    public int getEmailGroupIndex(String value) {
        List<String> accounts = getGroupLists();
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).contains(value))
                return i;
        }
        return -1;
    }

    public HTMLDiv getEmailGroup(int index) {
        emailGroupName.setLocator(String.format(emailGroupName.getRawLocator(), index));
        return emailGroupName;
    }
}
