package com.oracle.auto.testapp.web.pages.MenuServicePages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by grcao on 8/07/2017.
 */

@Component
public class TemplateMessagePage extends TestAppPageBase {
    private HTMLDiv mapBtn;

    private HTMLDiv searchInputBox;
    private HTMLDiv listedMsgs;
    private HTMLDiv exceptedMsg;


    private HTMLDiv msgTitle;
    private HTMLDiv editBtn;
    private HTMLDiv deleteMsgBtn;
    private HTMLDiv previewMsgBtn;

    private HTMLDiv confirmDeleteBtn;
    private HTMLDiv viewQRCode;
    private HTMLDiv closeQRCode;

    @PostConstruct
    public void init() {
        injectPageToChildComponents(this);
        waitForPageReady();
    }

    public void setMapBtn(HTMLDiv mapBtn) {
        this.mapBtn = mapBtn;
    }

    public HTMLDiv getMapBtn() {
        return mapBtn;
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


    public void setConfirmDeleteBtn(HTMLDiv confirmDeleteBtn) {
        this.confirmDeleteBtn = confirmDeleteBtn;
    }

    public HTMLDiv getConfirmDeleteBtn() {
        return confirmDeleteBtn;
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


    public void setEditBtn(HTMLDiv editBtn) {
        this.editBtn = editBtn;
    }

    public HTMLDiv getEditBtn() {
        return editBtn;
    }

    public void setDeleteMsgBtn(HTMLDiv deleteMsgBtn) {
        this.deleteMsgBtn = deleteMsgBtn;
    }

    public HTMLDiv getDeleteMsgBtn() {
        return deleteMsgBtn;
    }

    public void setPreviewMsgBtn(HTMLDiv previewMsgBtn) {
        this.previewMsgBtn = previewMsgBtn;
    }

    public HTMLDiv getPreviewMsgBtn() {
        return previewMsgBtn;
    }

    public void setMsgTitle(HTMLDiv msgTitle) {
        this.msgTitle = msgTitle;
    }

    public HTMLDiv getMsgTitle() {
        return msgTitle;
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
            accountList.add(getExceptedMsgTitle(i).getText());
        }
        return accountList;
    }


    public HTMLDiv getExceptedMsg(int index) {
        exceptedMsg.setLocator(String.format(exceptedMsg.getRawLocator(), index));
        return exceptedMsg;
    }

    public HTMLDiv getExceptedMsgTitle(int index) {
        msgTitle.setLocator(String.format(msgTitle.getRawLocator(), index));
        return msgTitle;
    }

    public HTMLDiv editExceptedMsg(int index) {
        editBtn.setLocator(String.format(editBtn.getRawLocator(), index));
        return editBtn;
    }

    public HTMLDiv deleteExceptedMsg(int index) {
        deleteMsgBtn.setLocator(String.format(deleteMsgBtn.getRawLocator(), index));
        return deleteMsgBtn;
    }

    public HTMLDiv previewExceptedMsg(int index) {
        previewMsgBtn.setLocator(String.format(previewMsgBtn.getRawLocator(), index));
        return previewMsgBtn;
    }
}
