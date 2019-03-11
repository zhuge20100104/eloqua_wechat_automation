package com.oracle.auto.testapp.web.pages.AssetsPages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by grace on 8/30/2017.
 */

@Component
public class EmailsMainPage extends TestAppPageBase {

    private HTMLDiv createEmailBtn;
    private HTMLDiv designEmailLink;

    private HTMLDiv blankEmailBtn;
    private HTMLDiv chooseBtn;


    @PostConstruct
    public void init() {
        registerComp(createEmailBtn);
        injectPageToChildComponents(this);
        waitForPageReady();
    }

    public void setCreateEmailBtn(HTMLDiv createEmailBtn) {
        this.createEmailBtn = createEmailBtn;
    }

    public HTMLDiv getCreateEmailBtn() {
        return createEmailBtn;
    }

    public void setBlankEmailBtn(HTMLDiv blankEmailBtn) {
        this.blankEmailBtn = blankEmailBtn;
    }

    public HTMLDiv getBlankEmailBtn() {
        return blankEmailBtn;
    }

    public void setDesignEmailLink(HTMLDiv designEmailLink) {
        this.designEmailLink = designEmailLink;
    }

    public HTMLDiv getDesignEmailLink() {
        return designEmailLink;
    }

    public void setChooseBtn(HTMLDiv chooseBtn) {
        this.chooseBtn = chooseBtn;
    }

    public HTMLDiv getChooseBtn() {
        return chooseBtn;
    }
}
