package com.oracle.auto.testapp.web.pages.SettingsPages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.testapp.web.model.WeChatFile;
import com.oracle.auto.web.comp.html.*;
import com.oracle.auto.web.utility.PropertyConfiger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by grcao on 7/3/2017.
 */

@Component
public class OfficialAccountSettingPage extends TestAppPageBase {
    private HTMLDiv authorizeButton;
    private HTMLDropDownList openIDFieldDropdownList;
    private HTMLInput defaultCampaignID;

    private HTMLDiv settingLink;
    private HTMLDiv advancedUserText;
    private HTMLDiv authorizedUsersBox;
    private HTMLDiv selectedUserBox;
    private HTMLTextArea usersInputBox;
    private HTMLDiv usersSearchBox;
    private HTMLDiv clearSelectedUsersBtn;
    private HTMLDiv exceptedUser;
    private HTMLDiv listedUsers;

    private HTMLDiv saveBtn;
    private HTMLDiv closeBtn;

    public void selectOpenID(String openIDValue) {
        Select sel = new Select(page.getDriver().findElement(By.id("openIdFieldId")));
        sel.selectByVisibleText(openIDValue);
    }

    @PostConstruct
    public void init() {
        injectPageToChildComponents(this);
        waitForPageReady();
    }

    public void setAuthorizeButton(HTMLDiv authorizeButton) {
        this.authorizeButton = authorizeButton;
    }

    public HTMLDiv getAuthorizeButton() {
        return authorizeButton;
    }

    public void setOpenIDFieldDropdownList(HTMLDropDownList openIDFieldDropdownList) {
        this.openIDFieldDropdownList = openIDFieldDropdownList;
    }

    public HTMLDropDownList getopenIDFieldDropdownList() {
        return openIDFieldDropdownList;
    }

    public void setDefaultCampaignID(HTMLInput defaultCampaignID) {
        this.defaultCampaignID = defaultCampaignID;
    }

    public HTMLInput getDefaultCampaignID() {
        return defaultCampaignID;
    }


    public void setAuthorizedUsersBox(HTMLDiv authorizedUsersBox) {
        this.authorizedUsersBox = authorizedUsersBox;
    }

    public HTMLDiv getAuthorizedUsersBox() {
        return authorizedUsersBox;
    }

    public void setSelectedUserBox(HTMLDiv selectedUserBox) {
        this.selectedUserBox = selectedUserBox;
    }

    public HTMLDiv getSelectedUserBox() {
        return selectedUserBox;
    }

    public void setAdvancedUserText(HTMLDiv advancedUserText) {
        this.advancedUserText = advancedUserText;
    }

    public HTMLDiv getAdvancedUserText() {
        return advancedUserText;
    }

    public void setUsersSearchBox(HTMLDiv usersSearchBox) {
        this.usersSearchBox = usersSearchBox;
    }

    public HTMLDiv getUsersSearchBox() {
        return usersSearchBox;
    }

    public void setUsersInputBox(HTMLTextArea usersInputBox) {
        this.usersInputBox = usersInputBox;
    }

    public HTMLTextArea getUsersInputBox() {
        return usersInputBox;
    }

    public void setExceptedUser(HTMLDiv exceptedUser) {
        this.exceptedUser = exceptedUser;
    }

    public HTMLDiv getExceptedUser() {
        return exceptedUser;
    }

    public void setListedUsers(HTMLDiv listedUsers) {
        this.listedUsers = listedUsers;
    }

    public HTMLDiv getListedUsers() {
        return listedUsers;
    }

    public void setSettingLink(HTMLDiv settingLink) {
        this.settingLink = settingLink;
    }

    public HTMLDiv getSettingLink() {
        return settingLink;
    }

    public void setCloseBtn(HTMLDiv closeBtn) {
        this.closeBtn = closeBtn;
    }

    public HTMLDiv getCloseBtn() {
        return closeBtn;
    }

    public void setSaveBtn(HTMLDiv saveBtn) {
        this.saveBtn = saveBtn;
    }

    public HTMLDiv getSaveBtn() {
        return saveBtn;
    }

    public void setClearSelectedUsersBtn(HTMLDiv clearSelectedUsersBtn) {
        this.clearSelectedUsersBtn = clearSelectedUsersBtn;
    }

    public HTMLDiv getClearSelectedUsersBtn() {
        return clearSelectedUsersBtn;
    }

    public void clearSelectedUsers() {
        if (getClearSelectedUsersBtn().isReady()) {
            getClearSelectedUsersBtn().click();
        }
    }

    public void downloadFile(WeChatFile file) {
        HTMLImg qrCode = new HTMLImg("BDD_jQuery('.js_qrcode')[0]");
        qrCode.setPage(page);
        String imageSource = qrCode.getImageSrc();
        File downloadFile = new File(file.path + file.name + ".png");
        try {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("cn-proxy.jp.oracle.com", 80));
            URL imageURL = new URL(imageSource);
            HttpURLConnection uc = (HttpURLConnection) imageURL.openConnection(proxy);
            InputStream inputStream = uc.getInputStream();

            FileOutputStream writer = new FileOutputStream(downloadFile);
            byte[] buffer = new byte[153600];
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) > 0)
                writer.write(buffer, 0, bytesRead);

            writer.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void waitForSomeTime(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
