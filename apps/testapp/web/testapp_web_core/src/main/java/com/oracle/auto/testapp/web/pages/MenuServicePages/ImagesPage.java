package com.oracle.auto.testapp.web.pages.MenuServicePages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.ext.ExtUploadFileButton;
import com.oracle.auto.web.comp.html.HTMLButton;
import com.oracle.auto.web.comp.html.HTMLDiv;
import com.oracle.auto.web.comp.html.HTMLInput;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by grcao on 13/3/2017.
 */

@Component
public class ImagesPage extends TestAppPageBase {

    private HTMLDiv uploadBtn;
    private HTMLDiv deleteMsgBtn;
    private HTMLDiv previewMsgBtn;
    private HTMLDiv confirmDeleteBtn;
    private HTMLDiv previewedImage;
    private HTMLDiv closePreviewedMsg;

    private HTMLDiv imageTitle;
    private HTMLDiv imageTitles;



    @PostConstruct
    public void init() {
        injectPageToChildComponents(this);
        waitForPageReady();
    }


    public void setUploadBtn(HTMLDiv uploadBtn) {
        this.uploadBtn = uploadBtn;
    }

    public HTMLDiv getUploadBtn() {
        return uploadBtn;
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


    public HTMLDiv getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(HTMLDiv imageTitle) {
        this.imageTitle = imageTitle;
    }

    public HTMLDiv getImageTitles() {
        return imageTitles;
    }

    public void setImageTitles(HTMLDiv imageTitles) {
        this.imageTitles = imageTitles;
    }

    public int getElementIndex(String title) {
        int numbers = getImageTitles().getMethodProp(".size()").AsInt();
        int index = -1;
        for (int j = 0; j < numbers; j++) {
            if (getExceptedImageTitle(j).getText().contains(title)) {
                index = j;
                break;
            }
        }
        return index;
    }

    public HTMLDiv getExceptedImageTitle(int index) {
        imageTitle.setLocator(String.format(imageTitle.getRawLocator(), index));
        return imageTitle;
    }

    public HTMLDiv getExceptedImageDeleteBtn(int index) {
        deleteMsgBtn.setLocator(String.format(deleteMsgBtn.getRawLocator(), index));
        return deleteMsgBtn;
    }

    public HTMLDiv getExceptedImagePreviewBtn(int index) {
        previewMsgBtn.setLocator(String.format(previewMsgBtn.getRawLocator(), index));
        return previewMsgBtn;
    }

    public void setConfirmDeleteBtn(HTMLDiv confirmDeleteBtn) {
        this.confirmDeleteBtn = confirmDeleteBtn;
    }

    public HTMLDiv getConfirmDeleteBtn() {
        return confirmDeleteBtn;
    }

    public HTMLDiv getPreviewedImage() {
        return previewedImage;
    }

    public void setPreviewedImage(HTMLDiv previewedImage) {
        this.previewedImage = previewedImage;
    }

    public HTMLDiv getClosePreviewedMsg() {
        return closePreviewedMsg;
    }

    public void setClosePreviewedMsg(HTMLDiv closePreviewedMsg) {
        this.closePreviewedMsg = closePreviewedMsg;
    }



    public void waitForSecond(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
