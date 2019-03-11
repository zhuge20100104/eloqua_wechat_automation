package com.oracle.auto.testapp.web.pages.MenuServicePages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.testapp.web.model.WeChatFile;
import com.oracle.auto.web.comp.html.HTMLDiv;
import com.oracle.auto.web.comp.html.HTMLImg;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by grcao on 7/27/2017.
 */

@Component
public class AutoReplyManagementPage extends TestAppPageBase {

    private HTMLDiv configureBtn;
    private HTMLDiv searchInputBox;
    private HTMLDiv listedMsgs;
    private HTMLDiv exceptedMsg;

    private HTMLDiv RMEditBtn;
    private HTMLDiv RMDeleteBtn;
    private HTMLDiv RMViewQRBtn;

    private HTMLDiv confirmDeleteRMMsg;
    private HTMLDiv viewQRCode;
    private HTMLDiv closeQRCode;


    @PostConstruct
    public void init() {
        injectPageToChildComponents(this);
        waitForPageReady();
    }

    public void setConfigureBtn(HTMLDiv configureBtn) {
        this.configureBtn = configureBtn;
    }

    public HTMLDiv getConfigureBtn() {
        return configureBtn;
    }

    public void setSearchInputBox(HTMLDiv searchInputBox) {
        this.searchInputBox = searchInputBox;
    }

    public HTMLDiv getSearchInputBox() {
        return searchInputBox;
    }

    public void setListedMsgs(HTMLDiv listedMsgs) {
        this.listedMsgs = listedMsgs;
    }

    public HTMLDiv getListedMsgs() {
        return listedMsgs;
    }

    public void setExceptedMsg(HTMLDiv exceptedMsg) {
        this.exceptedMsg = exceptedMsg;
    }

    public HTMLDiv getExceptedMsg() {
        return exceptedMsg;
    }

    public void setRMEditBtn(HTMLDiv rmEditBtn) {
        this.RMEditBtn = rmEditBtn;
    }

    public HTMLDiv getRMEditBtn() {
        return RMEditBtn;
    }

    public void setRMDeleteBtn(HTMLDiv rmDeleteBtn) {
        this.RMDeleteBtn = rmDeleteBtn;
    }

    public HTMLDiv getRMDeleteBtn() {
        return RMDeleteBtn;
    }

    public void setRMViewQRBtn(HTMLDiv rmViewQRBtn) {
        this.RMViewQRBtn = rmViewQRBtn;
    }

    public HTMLDiv getRMViewQRBtn() {
        return RMViewQRBtn;
    }

    public void setConfirmDeleteRMMsg(HTMLDiv confirmDeleteRMMsg) {
        this.confirmDeleteRMMsg = confirmDeleteRMMsg;
    }

    public HTMLDiv getConfirmDeleteRMMsg() {
        return confirmDeleteRMMsg;
    }

    public void setViewQRCode(HTMLDiv viewQRCode) {
        this.viewQRCode = viewQRCode;
    }

    public HTMLDiv getViewQRCode() {
        return viewQRCode;
    }

    public void setCloseQRCode(HTMLDiv closeQRCode) {
        this.closeQRCode = closeQRCode;
    }

    public HTMLDiv getCloseQRCode() {
        return closeQRCode;
    }

    public int getMessageIndex(String value) {
        List<String> accounts = getListsMessages();
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).contains(value))
                return i;
        }
        return -1;
    }

    public List<String> getListsMessages() {
        List<String> accountList = new ArrayList<>();
        int numbers = getListedMsgs().getMethodProp(".size()").AsInt();
        for (int i = 0; i < numbers; i++) {
            accountList.add(getExceptedMsg(i).getText());
        }
        return accountList;
    }

    public HTMLDiv getExceptedMsg(int index) {
        exceptedMsg.setLocator(String.format(exceptedMsg.getRawLocator(), index));
        return exceptedMsg;
    }

    public HTMLDiv editExceptedMsg(int index) {
        RMEditBtn.setLocator(String.format(RMEditBtn.getRawLocator(), index));
        return RMEditBtn;
    }

    public HTMLDiv deleteExceptedMsg(int index) {
        RMDeleteBtn.setLocator(String.format(RMDeleteBtn.getRawLocator(), index));
        return RMDeleteBtn;
    }

    public HTMLDiv viewExceptedMsg(int index) {
        RMViewQRBtn.setLocator(String.format(RMViewQRBtn.getRawLocator(), index));
        return RMViewQRBtn;
    }

    public void downloadFile(WeChatFile file) {
        HTMLImg qrCode = new HTMLImg("id=qrcode_img");
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

    public void waitForSecond(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
