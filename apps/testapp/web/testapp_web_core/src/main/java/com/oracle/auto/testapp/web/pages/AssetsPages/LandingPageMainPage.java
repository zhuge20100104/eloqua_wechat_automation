package com.oracle.auto.testapp.web.pages.AssetsPages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import com.oracle.auto.web.comp.html.HTMLInput;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by grace on 8/29/2017.
 */

@Component
public class LandingPageMainPage extends TestAppPageBase {

    private HTMLDiv createLandingPageBtn;

    private HTMLDiv chooseBtn;
    private HTMLDiv createBlankLPBtn;
    private HTMLDiv createHTMLLPBtn;

    @PostConstruct
    public void init() {
        registerComp(createLandingPageBtn);
        injectPageToChildComponents(this);
        waitForPageReady();
    }

    public void setCreateLandingPageBtn(HTMLDiv createLandingPageBtn) {
        this.createLandingPageBtn = createLandingPageBtn;
    }

    public HTMLDiv getCreateLandingPageBtn() {
        return createLandingPageBtn;
    }

    public void setCreateBlankLPBtn(HTMLDiv createBlankLPBtn) {
        this.createBlankLPBtn = createBlankLPBtn;
    }

    public HTMLDiv getCreateBlankLPBtn() {
        return createBlankLPBtn;
    }

    public void setCreateHTMLLPBtn(HTMLDiv createHTMLLPBtn) {
        this.createHTMLLPBtn = createHTMLLPBtn;
    }

    public HTMLDiv getCreateHTMLLPBtn() {
        return createHTMLLPBtn;
    }

    public void setChooseBtn(HTMLDiv chooseBtn) {
        this.chooseBtn = chooseBtn;
    }

    public HTMLDiv getChooseBtn() {
        return chooseBtn;
    }
}
