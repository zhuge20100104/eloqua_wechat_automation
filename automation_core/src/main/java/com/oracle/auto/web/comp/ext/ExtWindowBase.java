package com.oracle.auto.web.comp.ext;


import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;

// xtype: window
public class ExtWindowBase extends ExtComponentBase {
	private static String textboxRelativeLocator = ".ExtQuery('textfield{isVisible()}')[%d]";
	private static String textfiledByPropertyRelativeLocator = ".ExtQuery('[%s=%s]{isVisible()}')[0]";
	private static String numberboxRelativeLocator = ".ExtQuery('numberfield{isVisible()}')[%d]";
	private static String btnRelativeLocator = ".ExtQuery('button{isVisible()}')[%d]";
	
	private static String valueDisplayRelativeLocator = ".ExtQuery('displayfield{isVisible()}')[%d]";
	private static String textAreaRelativeLocator = ".ExtQuery('textarea{isVisible()}')[%d]";
	
	private static String richTextRelativeLocator = ".ExtQuery('xcp_rich_text{isVisible()}')[%d]";

	// no wait dialog
	public ExtWindowBase(WebDriverSeleniumPageEx page, String locator) {
		super(page, locator);
	}
	
	public  String getTitle() {
		waitForReadyEnabled();

		return getMethodPropIgnoreUndefined(".title").str();
	}

	public void close() {
		waitForReadyEnabled();

		setNoReturnMethodProp(".close()");
	}
	
	public void typeTextBox(int index, String value){
		waitForReadyEnabled();
		
		ExtTextbox box = new ExtTextbox(page, String.format(locator + textboxRelativeLocator, index)); 
		box.setValue(value);
	}
	
	public void typeTextAreaValue(int index, String value) {
		waitForReadyEnabled();
		
		ExtTextbox box = new ExtTextbox(page, String.format(locator + textAreaRelativeLocator, index));
		box.setValue(value);
	}
	
	public String getTextAreaValue(int index) {
		waitForReadyEnabled();
		
		ExtTextbox box = new ExtTextbox(page, String.format(locator + textAreaRelativeLocator, index));
		return box.getValue();
	}
	
	// text field: textbox or text area
	public String getTexFieldValueByProperty(String properName, String properValue) {
		waitForReadyEnabled();
		
		ExtTextbox box = new ExtTextbox(page, String.format(locator + textfiledByPropertyRelativeLocator, properName, properValue));
		return box.getValue();
	}
		
	// text field: text box or text area
	public void typeTextFieldByProperty(String properName, String properValue, String value) {
		waitForReadyEnabled();
		
		ExtTextbox box = new ExtTextbox(page, String.format(locator + textfiledByPropertyRelativeLocator, properName, properValue));
		box.setValue(value);
	}
	
	public void typeTextBoxAsNumber(int index, int value){
		waitForReadyEnabled();
		
		ExtNumberBox box = new ExtNumberBox(page, String.format(locator + numberboxRelativeLocator, index)); 
		box.setValue(value);
	}
	
	public void clickButton(int index) {
		waitForReadyEnabled();
		
		ExtButton btn = new ExtButton(page, String.format(locator + btnRelativeLocator, index)); 
		btn.click();
	}
	
	public String getValueDisplayString(int index) {
		waitForReadyEnabled();
		
		ExtValueDisplay vd = new ExtValueDisplay(page, String.format(locator + valueDisplayRelativeLocator, index));
		return vd.getDisplayValue();		
	}

	public String getRichTextValue(int index) {
		waitForReadyEnabled();
		
		ExtTextLink text = new ExtTextLink(page, String.format(locator + richTextRelativeLocator, index));
		return text.getCaption();
	}
	
}
