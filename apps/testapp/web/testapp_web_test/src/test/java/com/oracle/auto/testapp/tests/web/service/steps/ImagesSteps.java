package com.oracle.auto.testapp.tests.web.service.steps;


import com.oracle.auto.testapp.web.pages.MenuServicePages.*;
import com.oracle.auto.testapp.web.pages.TestAppPageFactory;
import com.oracle.auto.web.utility.PropertyConfiger;
import com.oracle.auto.web.utility.WebAssert;
import org.jbehave.core.annotations.Then;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by grcao on 13/3/2017.
 */

public class ImagesSteps {
    private TestAppPageFactory factory;

    List<String> files = Arrays.asList("AutoImage1.png", "AutoImage2.png", "AutoImage3.png", "AutoImage4.png", "AutoImage5.png");
    String filePath = PropertyConfiger.instance().getEnvData("wechat.test.images", "");

    public ImagesSteps() {
        factory = TestAppPageFactory.getInstance();
    }


    @Then("click images message link")
    public void clickImagesLink() {
        MenuServicePage menuServicePage = factory.pageAs(MenuServicePage.class);
        menuServicePage.goToImagePage();
        menuServicePage.waitForSecond(5);
    }

    @Then("click upload image button to upload files")
    public void clickUploadImageBtnToUploadFiles() {
        ImagesPage imagesPage = factory.pageAs(ImagesPage.class);
        imagesPage.waitForSecond(2);
        imagesPage.getUploadBtn().click();

        imagesPage.page().executeScript("document.getElementById(\"file-upload-module\").style.display=\"block\"");
        imagesPage.page().executeScript("document.getElementsByName(\"submit1\")[0].style.display=\"block\"");
        imagesPage.page().executeScript("document.getElementById(\"userFileSet1\").style.display=\"block\"");

        String keys = "";
        for (String file : files) {
            if (!keys.isEmpty()) keys += "\n";
            keys += filePath + file;
        }
        imagesPage.page().sendkeys("id=userFileSet1", keys);
        // imagesPage.page().sendkeys("//input[@name=\"submit1\"]", path);
    }

    @Then("Verify if the files are uploaded successfully")
    public void checkFilesUploaded() {
        ImagesPage imagesPage = factory.pageAs(ImagesPage.class);

        List<String> uploadedFiles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            uploadedFiles.add(imagesPage.getExceptedImageTitle(i).getText());
        }

        WebAssert.assertEqualsIgnoreOrder("Files are uploaded successfully", files, uploadedFiles);


    }

    @Then("click image preview button: $imageTitle")
    public void clickPreviewImageBtn(String imageTitle) {
        ImagesPage imagesPage = factory.pageAs(ImagesPage.class);
        imagesPage.page().waitForSecond(5);
        imagesPage.getExceptedImageTitle(0).waitForElementReady(10);
        int index = imagesPage.getElementIndex(imageTitle);
        imagesPage.getExceptedImageTitle(index).click();
        imagesPage.getExceptedImagePreviewBtn(index).click();
    }

    @Then("delete image with title: $imageTitle")
    public void deleteExistedTemplateMsg(String imageTitle) {
        ImagesPage imagesPage = factory.pageAs(ImagesPage.class);
        int index = imagesPage.getElementIndex(imageTitle);
        imagesPage.getExceptedImageTitle(index).click();
        imagesPage.getExceptedImageDeleteBtn(index).click();
        imagesPage.page().waitForSecond(1);
        imagesPage.getConfirmDeleteBtn().click();
        imagesPage.page().waitForSecond(5);
    }

    @Then("delete all automation images: $msgTitle")
    public void deleteUnusedExistedMsgs(String imageTitle) {

        ImagesPage imagesPage = factory.pageAs(ImagesPage.class);
        imagesPage.page().waitForSecond(5);
        imagesPage.getExceptedImageTitle(0).waitForElementReady(60);
        int index = 1;
        while (index != -1) {
            index = imagesPage.getElementIndex(imageTitle);
            if (index != -1) {
                imagesPage.getExceptedImageTitle(index).click();
                imagesPage.getExceptedImageDeleteBtn(index).click();
                imagesPage.page().waitForSecond(1);
                imagesPage.getConfirmDeleteBtn().click();
                imagesPage.page().waitForSecond(5);
            } else {
                break;
            }

        }
    }

    @Then("verify if image can be previewed: $imageTitle")
    public void verifyIfImagePreviewed(String imageTitle) {
        ImagesPage imagesPage = factory.pageAs(ImagesPage.class);
        clickPreviewImageBtn(imageTitle);

        imagesPage.getPreviewedImage().waitForElementReady(60);
        WebAssert.assertTrue(imagesPage.getPreviewedImage().isReady());
        imagesPage.page().waitForSecond(1);
        imagesPage.getClosePreviewedMsg().click();
    }

}
