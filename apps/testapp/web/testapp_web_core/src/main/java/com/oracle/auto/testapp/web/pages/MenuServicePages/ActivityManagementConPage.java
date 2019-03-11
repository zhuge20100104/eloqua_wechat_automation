package com.oracle.auto.testapp.web.pages.MenuServicePages;

import com.oracle.auto.testapp.web.ancestor.TestAppPageBase;
import com.oracle.auto.web.comp.html.HTMLDiv;
import com.oracle.auto.web.comp.html.HTMLTextArea;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by grcao on 8/02/2017.
 */

@Component
public class ActivityManagementConPage extends TestAppPageBase {

    private HTMLDiv selectObjectArrow;
    private HTMLDiv objectSearchBox;
    private HTMLDiv listedObjects;
    private HTMLDiv exceptedObject;
    private HTMLDiv confirmObjectChangedBtn;

    private HTMLDiv addRuleBtn;
    private HTMLDiv addConditionBtn;

    private HTMLDiv conditionArrow;
    private HTMLDiv conditionSearchBox;
    private HTMLDiv listedConditions;
    private HTMLDiv exceptedCondition;

    private HTMLDiv symbolArrow;
    private HTMLDiv listedSymbols;
    private HTMLDiv exceptedSymbols;
    private HTMLDiv conditionValue;


    private HTMLDiv fieldUpdateBtn;
    private HTMLDiv fieldArrow;
    private HTMLDiv updateFieldSearchBox;
    private HTMLDiv listedUpdateItems;
    private HTMLDiv exceptedField;
    private HTMLDiv fieldValue;


    private HTMLDiv ruleMsgSendBtn;
    private HTMLDiv ruleMsgTypeArrow;
    private HTMLDiv ruleMsgEditBtn;
    private HTMLDiv ruleListedMsgType;
    private HTMLDiv ruleExceptedMsgType;
    private HTMLDiv ruleMsgTextInputBox;
    private HTMLDiv ruleMsgTextSearchBox;
    private HTMLDiv ruleMsgTextListedInsertItems;
    private HTMLDiv ruleMsgTextExceptedInsertItem;
    private HTMLDiv ruleMsgTextCloseBtn;
    private HTMLDiv ruleRichMediaNews;
    private HTMLDiv mediaNewsSelected;

    private HTMLDiv defaultRuleMsgSendBtn;
    private HTMLDiv defaultRuleMsgTypeArrow;
    private HTMLDiv defaultRuleMsgEditBtn;
    private HTMLDiv defaultRuleListedMsgType;
    private HTMLDiv defaultRuleExceptedMsgType;
    private HTMLTextArea defaultRuleMsgTextInputBox;
    private HTMLDiv defaultRuleMsgTextSearchBox;
    private HTMLDiv defaultRuleMsgTextListedInsertItems;
    private HTMLDiv defaultRuleMsgTextExceptedInsertItem;
    private HTMLDiv defaultRuleMsgTextCloseBtn;

//    private HTMLDiv listedInsertItems;
//    private HTMLDiv exceptedInsertItem;
//    private HTMLDiv closeInsertField;
//    private HTMLDiv saveBtn;


    @PostConstruct
    public void init() {
        injectPageToChildComponents(this);
        waitForPageReady();
    }

    public void setSelectObjectArrow(HTMLDiv selectObjectArrow) {
        this.selectObjectArrow = selectObjectArrow;
    }

    public HTMLDiv getSelectObjectArrow() {
        return selectObjectArrow;
    }

    public void setObjectSearchBox(HTMLDiv objectSearchBox) {
        this.objectSearchBox = objectSearchBox;
    }

    public HTMLDiv getObjectSearchBox() {
        return objectSearchBox;
    }

    public void setListedObjects(HTMLDiv listedObjects) {
        this.listedObjects = listedObjects;
    }

    public HTMLDiv getListedObjects() {
        return listedObjects;
    }

    public void setExceptedObject(HTMLDiv exceptedObject) {
        this.exceptedObject = exceptedObject;
    }

    public HTMLDiv getExceptedObject() {
        return exceptedObject;
    }

    public void setConfirmObjectChangedBtn(HTMLDiv confirmObjectChangedBtn) {
        this.confirmObjectChangedBtn = confirmObjectChangedBtn;
    }

    public HTMLDiv getConfirmObjectChangedBtn() {
        return confirmObjectChangedBtn;
    }

    public int getObjectIndex(String value) {
        List<String> items = getListsObjectItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).contains(value))
                return i;
        }
        return -1;
    }

    public List<String> getListsObjectItems() {
        List<String> itemList = new ArrayList<>();
        int numbers = getListedObjects().getMethodProp(".size()").AsInt();
        for (int i = 0; i < numbers; i++) {
            itemList.add(getExceptedObject(i).getText());
        }
        return itemList;
    }

    public HTMLDiv getExceptedObject(int index) {
        exceptedObject.setLocator(String.format(exceptedObject.getRawLocator(), index));
        return exceptedObject;
    }

    public void setAddRuleBtn(HTMLDiv addRuleBtn) {
        this.addRuleBtn = addRuleBtn;
    }

    public HTMLDiv getAddRuleBtn() {
        return addRuleBtn;
    }

    public void setAddConditionBtn(HTMLDiv addConditionBtn) {
        this.addConditionBtn = addConditionBtn;
    }

    public HTMLDiv getAddConditionBtn() {
        return addConditionBtn;
    }

    public void setFieldUpdateBtn(HTMLDiv fieldUpdateBtn) {
        this.fieldUpdateBtn = fieldUpdateBtn;
    }

    public HTMLDiv getFieldUpdateBtn() {
        return fieldUpdateBtn;
    }

    public void setRuleMsgSendBtn(HTMLDiv ruleMsgSendBtn) {
        this.ruleMsgSendBtn = ruleMsgSendBtn;
    }

    public HTMLDiv getRuleMsgSendBtn() {
        return ruleMsgSendBtn;
    }

    public void setDefaultRuleMsgSendBtn(HTMLDiv defaultRuleMsgSendBtn) {
        this.defaultRuleMsgSendBtn = defaultRuleMsgSendBtn;
    }

    public HTMLDiv getDefaultRuleMsgSendBtn() {
        return defaultRuleMsgSendBtn;
    }

    public void setConditionArrow(HTMLDiv conditionArrow) {
        this.conditionArrow = conditionArrow;
    }

    public HTMLDiv getConditionArrow() {
        return conditionArrow;
    }

    public HTMLDiv getConditionArrow(int index) {
        //return conditionArrow;
        conditionArrow.setLocator(String.format(conditionArrow.getRawLocator(), index));
        return conditionArrow;
    }

    public void setConditionSearchBox(HTMLDiv conditionSearchBox) {
        this.conditionSearchBox = conditionSearchBox;
    }

    public HTMLDiv getConditionSearchBox() {
        return conditionSearchBox;
    }

    public void setListedConditions(HTMLDiv listedConditions) {
        this.listedConditions = listedConditions;
    }

    public HTMLDiv getListedConditions() {
        return listedConditions;
    }

    public void setExceptedCondition(HTMLDiv exceptedCondition) {
        this.exceptedCondition = exceptedCondition;
    }

    public HTMLDiv getExceptedCondition() {
        return exceptedCondition;
    }

    public int getConditionIndex(String value) {
        List<String> items = getListsConditionItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).contains(value))
                return i;
        }
        return -1;
    }

    public List<String> getListsConditionItems() {
        List<String> itemList = new ArrayList<>();
        int numbers = getListedConditions().getMethodProp(".size()").AsInt();
        for (int i = 0; i < numbers; i++) {
            itemList.add(getExceptedCondition(i).getText());
        }
        return itemList;
    }

    public HTMLDiv getExceptedCondition(int index) {
        exceptedCondition.setLocator(String.format(exceptedCondition.getRawLocator(), index));
        return exceptedCondition;
    }


    public void setSymbolArrow(HTMLDiv symbolArrow) {
        this.symbolArrow = symbolArrow;
    }

    public HTMLDiv getSymbolArrow() {
        return symbolArrow;
    }

    public HTMLDiv getSymbolArrow(int index) {
        symbolArrow.setLocator(String.format(symbolArrow.getRawLocator(), index));
        return symbolArrow;
    }

    public void setListedSymbols(HTMLDiv listedSymbols) {
        this.listedSymbols = listedSymbols;
    }

    public HTMLDiv getListedSymbols() {
        return listedSymbols;
    }

    public void setExceptedSymbols(HTMLDiv exceptedSymbols) {
        this.exceptedSymbols = exceptedSymbols;
    }

    public HTMLDiv getExceptedSymbols() {
        return exceptedSymbols;
    }

    public HTMLDiv getConditionSymbolIndex(String value) {
        int index = -1;
        switch (value) {
            case "equals to":
                //  case "无":
                index = 0;
                break;
            case "not equals to":
                index = 1;
                break;
            case "is blank":
                // case "微信 ID":
                index = 2;
                break;
            case "is not blank":
                //case "微信信息":
                index = 3;
                break;
            default:
                break;

        }
        exceptedSymbols.setLocator(String.format(exceptedSymbols.getRawLocator(), index));
        return exceptedSymbols;
    }


    public void setConditionValue(HTMLDiv conditionValue) {
        this.conditionValue = conditionValue;
    }

    public HTMLDiv getConditionValue() {
        return conditionValue;
    }

    public HTMLDiv getConditionValue(int index) {
        conditionValue.setLocator(String.format(conditionValue.getRawLocator(), index));
        return conditionValue;
    }


    public void setFieldArrow(HTMLDiv fieldArrow) {
        this.fieldArrow = fieldArrow;
    }

    public HTMLDiv getFieldArrow() {
        return fieldArrow;
    }

    public void setListedUpdateItems(HTMLDiv listedUpdateItems) {
        this.listedUpdateItems = listedUpdateItems;
    }

    public HTMLDiv getListedUpdateItems() {
        return listedUpdateItems;
    }

    public void setExceptedField(HTMLDiv exceptedField) {
        this.exceptedField = exceptedField;
    }

    public HTMLDiv getExceptedField() {
        return exceptedField;
    }

    public void setUpdateFieldSearchBox(HTMLDiv updateFieldSearchBox) {
        this.updateFieldSearchBox = updateFieldSearchBox;
    }

    public HTMLDiv getUpdateFieldSearchBox() {
        return updateFieldSearchBox;
    }

    public void setFieldValue(HTMLDiv fieldValue) {
        this.fieldValue = fieldValue;
    }

    public HTMLDiv getFieldValue() {
        return fieldValue;
    }


    public int getFieldIndex(String value) {

        //Get listed items with content
        List<String> items = new ArrayList<>();
        int numbers = getListedUpdateItems().getMethodProp(".size()").AsInt();
        for (int i = 0; i < numbers; i++) {
            items.add(getExceptedObject(i).getText());
        }

        //Check if there is excepted item and return the index
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).contains(value))
                return i;
        }
        return -1;
    }


    public HTMLDiv getExceptedField(int index) {
        exceptedField.setLocator(String.format(exceptedField.getRawLocator(), index));
        return exceptedField;
    }


    public void setRuleMsgTypeArrow(HTMLDiv ruleMsgTypeArrow) {
        this.ruleMsgTypeArrow = ruleMsgTypeArrow;
    }

    public HTMLDiv getRuleMsgTypeArrow() {
        return ruleMsgTypeArrow;
    }

    public void setRuleMsgEditBtn(HTMLDiv ruleMsgEditBtn) {
        this.ruleMsgEditBtn = ruleMsgEditBtn;
    }

    public HTMLDiv getRuleMsgEditBtn() {
        return ruleMsgEditBtn;
    }

    public void setRuleListedMsgType(HTMLDiv ruleListedMsgType) {
        this.ruleListedMsgType = ruleListedMsgType;
    }

    public HTMLDiv getRuleListedMsgType() {
        return ruleListedMsgType;
    }

    public void setRuleExceptedMsgType(HTMLDiv ruleExceptedMsgType) {
        this.ruleExceptedMsgType = ruleExceptedMsgType;
    }

    public HTMLDiv getRuleExceptedMsgType() {
        return ruleExceptedMsgType;
    }

    public HTMLDiv getRuleMsgTypeIndex(String value) {
        int index = getMsgTypeIndex(value);
        ruleExceptedMsgType.setLocator(String.format(ruleExceptedMsgType.getRawLocator(), index));
        return ruleExceptedMsgType;
    }

    public int getMsgTypeIndex(String value) {
        switch (value) {
            case "Text Message":
                //  case "无":
                return 0;
            case "Rich Media Message":
                return 1;
            case "Template Message":
                // case "微信 ID":
                return 2;
            default:
                return -1;

        }
    }


    public void setRuleMsgTextInputBox(HTMLDiv ruleMsgTextInputBox) {
        this.ruleMsgTextInputBox = ruleMsgTextInputBox;
    }

    public HTMLDiv getRuleMsgTextInputBox() {
        return ruleMsgTextInputBox;
    }

    public void setRuleMsgTextSearchBox(HTMLDiv ruleMsgTextSearchBox) {
        this.ruleMsgTextSearchBox = ruleMsgTextSearchBox;
    }

    public HTMLDiv getRuleMsgTextSearchBox() {
        return ruleMsgTextSearchBox;
    }

    public void setRuleMsgTextListedInsertItems(HTMLDiv ruleMsgTextListedInsertItems) {
        this.ruleMsgTextListedInsertItems = ruleMsgTextListedInsertItems;
    }

    public HTMLDiv getRuleMsgTextListedInsertItems() {
        return ruleMsgTextListedInsertItems;
    }

    public void setRuleMsgTextExceptedInsertItem(HTMLDiv ruleMsgTextExceptedInsertItem) {
        this.ruleMsgTextExceptedInsertItem = ruleMsgTextExceptedInsertItem;
    }

    public HTMLDiv getRuleMsgTextExceptedInsertItem() {
        return ruleMsgTextExceptedInsertItem;
    }

    public HTMLDiv getRuleMsgTextExceptedInsertItem(String value) {

        //Get listed items with content
        List<String> items = new ArrayList<>();
        int numbers = getRuleMsgTextListedInsertItems().getMethodProp(".size()").AsInt();
        for (int i = 0; i < numbers; i++) {
            items.add(getExceptedInsertItem(i).getText());
        }

        //Check if there is excepted item and return the index
        int index = -1;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).equals(value))
                index = i;
        }

        return getExceptedInsertItem(index);
    }

    public HTMLDiv getExceptedInsertItem(int index) {
        ruleMsgTextExceptedInsertItem.setLocator(String.format(ruleMsgTextExceptedInsertItem.getRawLocator(), index));
        return ruleMsgTextExceptedInsertItem;
    }


    public void setRuleRichMediaNews(HTMLDiv ruleRichMediaNews) {
        this.ruleRichMediaNews = ruleRichMediaNews;
    }

    public HTMLDiv getRuleRichMediaNews() {
        return ruleRichMediaNews;
    }

    public HTMLDiv getRandomRichMediaNews() {
        Random random = new Random();
        int richMediaIndex = random.nextInt(5);
        ruleRichMediaNews.setLocator(String.format(ruleRichMediaNews.getRawLocator(), richMediaIndex));
        return ruleRichMediaNews;
    }

    private HTMLDiv ruleTemplateNews;

    public void setRuleTemplateNews(HTMLDiv ruleTemplateNews) {
        this.ruleTemplateNews = ruleTemplateNews;
    }

    public HTMLDiv getRuleTemplateNews() {
        return ruleTemplateNews;
    }

    public HTMLDiv getRuleTemplateNews(int index){
        ruleTemplateNews.setLocator(String.format(ruleTemplateNews.getRawLocator(),index));
        return ruleTemplateNews;
    }

    public HTMLDiv getRandomTemplateNews() {
        Random random = new Random();
        int richMediaIndex = random.nextInt(5);
        return getRuleTemplateNews(richMediaIndex);
    }

    public void setMediaNewsSelected(HTMLDiv mediaNewsSelected) {
        this.mediaNewsSelected = mediaNewsSelected;
    }

    public HTMLDiv getMediaNewsSelected() {
        return mediaNewsSelected;
    }

    public void setRuleMsgTextCloseBtn(HTMLDiv ruleMsgTextCloseBtn) {
        this.ruleMsgTextCloseBtn = ruleMsgTextCloseBtn;
    }

    public HTMLDiv getRuleMsgTextCloseBtn() {
        return ruleMsgTextCloseBtn;
    }


    public void setDefaultRuleMsgTypeArrow(HTMLDiv defaultRuleMsgTypeArrow) {
        this.defaultRuleMsgTypeArrow = defaultRuleMsgTypeArrow;
    }

    public HTMLDiv getDefaultRuleMsgTypeArrow() {
        return defaultRuleMsgTypeArrow;
    }

    public void setDefaultRuleMsgEditBtn(HTMLDiv defaultRuleMsgEditBtn) {
        this.defaultRuleMsgEditBtn = defaultRuleMsgEditBtn;
    }

    public HTMLDiv getDefaultRuleMsgEditBtn() {
        return defaultRuleMsgEditBtn;
    }

    public void setDefaultRuleListedMsgType(HTMLDiv defaultRuleListedMsgType) {
        this.defaultRuleListedMsgType = defaultRuleListedMsgType;
    }

    public HTMLDiv getDefaultRuleListedMsgType() {
        return defaultRuleListedMsgType;
    }

    public void setDefaultRuleExceptedMsgType(HTMLDiv defaultRuleExceptedMsgType) {
        this.defaultRuleExceptedMsgType = defaultRuleExceptedMsgType;
    }

    public HTMLDiv getDefaultRuleExceptedMsgType() {
        return defaultRuleExceptedMsgType;
    }

    public HTMLDiv getDefaultRuleMsgTypeIndex(String value) {
        int index = getMsgTypeIndex(value);
        defaultRuleExceptedMsgType.setLocator(String.format(defaultRuleExceptedMsgType.getRawLocator(), index));
        return defaultRuleExceptedMsgType;
    }

    public void setDefaultRuleMsgTextInputBox(HTMLTextArea defaultRuleMsgTextInputBox) {
        this.defaultRuleMsgTextInputBox = defaultRuleMsgTextInputBox;
    }

    public HTMLTextArea getDefaultRuleMsgTextInputBox() {
        return defaultRuleMsgTextInputBox;
    }

    public void setDefaultRuleMsgTextSearchBox(HTMLDiv defaultRuleMsgTextSearchBox) {
        this.defaultRuleMsgTextSearchBox = defaultRuleMsgTextSearchBox;
    }

    public HTMLDiv getDefaultRuleMsgTextSearchBox() {
        return defaultRuleMsgTextSearchBox;
    }

    public void setDefaultRuleMsgTextListedInsertItems(HTMLDiv defaultRuleMsgTextListedInsertItems) {
        this.defaultRuleMsgTextListedInsertItems = defaultRuleMsgTextListedInsertItems;
    }

    public HTMLDiv getDefaultRuleMsgTextListedInsertItems() {
        return defaultRuleMsgTextListedInsertItems;
    }

    public void setDefaultRuleMsgTextExceptedInsertItem(HTMLDiv defaultRuleMsgTextExceptedInsertItem) {
        this.defaultRuleMsgTextExceptedInsertItem = defaultRuleMsgTextExceptedInsertItem;
    }

    public HTMLDiv getDefaultRuleMsgTextExceptedInsertItem() {
        return defaultRuleMsgTextExceptedInsertItem;
    }


    public HTMLDiv getDefaultRuleMsgTextExceptedInsertItem(String value) {
        //Get listed items with content
        List<String> items = new ArrayList<>();
        int numbers = getDefaultRuleMsgTextListedInsertItems().getMethodProp(".size()").AsInt();
        for (int i = 0; i < numbers; i++) {
            items.add(getDefaultRuleExceptedInsertItem(i).getText());
        }

        //Check if there is excepted item and return the index
        int index = -1;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).equals(value))
                index = i;
        }

        return getDefaultRuleExceptedInsertItem(index);
    }

    public HTMLDiv getDefaultRuleExceptedInsertItem(int index) {
        defaultRuleMsgTextExceptedInsertItem.setLocator(String.format(defaultRuleMsgTextExceptedInsertItem.getRawLocator(), index));
        return defaultRuleMsgTextExceptedInsertItem;
    }

    public void setDefaultRuleMsgTextCloseBtn(HTMLDiv defaultRuleMsgTextCloseBtn) {
        this.defaultRuleMsgTextCloseBtn = defaultRuleMsgTextCloseBtn;
    }

    public HTMLDiv getDefaultRuleMsgTextCloseBtn() {
        return defaultRuleMsgTextCloseBtn;
    }


//    public HTMLDiv getRandomRichMedia() {
//        Random random = new Random();
//        int richMediaIndex = random.nextInt(3);
//        richMedia.setLocator(String.format(richMedia.getRawLocator(), richMediaIndex));
//        return richMedia;
//    }
//
//    public void setSaveBtn(HTMLDiv saveBtn) {
//        this.saveBtn = saveBtn;
//    }
//
//    public HTMLDiv getSaveBtn() {
//        return saveBtn;
//    }
//

//
//    public void setInsertFieldSearchBox(HTMLDiv insertFieldSearchBox) {
//        this.insertFieldSearchBox = insertFieldSearchBox;
//    }
//
//    public HTMLDiv getInsertFieldSearchBox() {
//        return insertFieldSearchBox;
//    }
//
//    public void setListedInsertItems(HTMLDiv listedInsertItems) {
//        this.listedInsertItems = listedInsertItems;
//    }
//
//    public HTMLDiv getListedInsertItems() {
//        return listedInsertItems;
//    }
//
//    public void setExceptedInsertItem(HTMLDiv exceptedInsertItem) {
//        this.exceptedInsertItem = exceptedInsertItem;
//    }
//
//    public HTMLDiv getExceptedInsertItem() {
//        return exceptedInsertItem;
//    }
//
//    public void setCloseInsertField(HTMLDiv closeInsertField) {
//        this.closeInsertField = closeInsertField;
//    }
//
//    public HTMLDiv getCloseInsertField() {
//        return closeInsertField;
//    }

//    public int getInsertItemIndex(String value) {
//        List<String> items = getListsInsertTextItems();
//        for (int i = 0; i < items.size(); i++) {
//            if (items.get(i).contains(value))
//                return i;
//        }
//        return -1;
//    }
//
//    public List<String> getListsInsertTextItems() {
//        List<String> itemList = new ArrayList<>();
//        int numbers = getListedInsertItems().getMethodProp(".size()").AsInt();
//        for (int i = 0; i < numbers; i++) {
//            itemList.add(getExceptedInsertItem(i).getText());
//        }
//        return itemList;
//    }
//
//    public HTMLDiv getExceptedInsertItem(int index) {
//        exceptedInsertItem.setLocator(String.format(exceptedInsertItem.getRawLocator(), index));
//        return exceptedInsertItem;
//    }


    public void waitForSecond(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
