package com.oracle.auto.web.comp.ext;

import com.oracle.auto.web.comp.ComponentAdaptorBase;
import com.oracle.auto.web.exceptions.ComponentGetPropertyException;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;
import org.apache.log4j.Logger;

// messagebox
public class ExtMessageBoxBase extends ExtComponentBase {
	private static Logger log = Logger.getLogger(ExtMessageBoxBase.class);
	private static String msgBoxLocator = "BDD_ExtQuery('messagebox')[0]";

	// no wait dialog
	public ExtMessageBoxBase(WebDriverSeleniumPageEx page) {
		super(page, msgBoxLocator);
	}
	
	public  String getTitle() {
		waitForReadyEnabled();

		return getMethodPropIgnoreUndefined(".title").str();
	}

	public  String getText() {
		waitForReadyEnabled();

		return getMethodPropIgnoreUndefined(".msg.value").str();
	}

	public void close() {
		waitForReadyEnabled();

		setNoReturnMethodProp(".close()");
	}
	
	// itemId: ok, yes, no, cancel
	private int getVisibleMsgButtonIndex(String itemId) {
		try {
			String script = String.format("var lst = %s.msgButtons; for (i=0; i<lst.length;i++) { if (lst[i].itemId == '%s' && lst[i].isVisible()) return i;} return -1;",
					locator, ComponentAdaptorBase.escape2JS(itemId));
			return page.executeScriptEx(script).AsInt();
		} catch (Exception ex) {
			throw new ComponentGetPropertyException(page,locator, ".msgButtons.itemId {isVisible()} == " + itemId, ex);
		}
	}
	
	public void clickYesOK() {
		waitForReadyEnabled();

		int btnIndex = getVisibleMsgButtonIndex("yes");
		if (btnIndex < 0)
			btnIndex = getVisibleMsgButtonIndex("ok");
		if (btnIndex < 0)
			throw new ComponentGetPropertyException(page,locator, ".msgButtons.itemId {isVisibile()} == yes/ok", "cannot find the visible button.");
		
		ExtButton btn = new ExtButton(page, String.format(msgBoxLocator + ".msgButtons[%d]", btnIndex));
		btn.click();
	}
	public void clickNoCancel() {
		waitForReadyEnabled();

		close(); // directly call close instead
	}
	
	public boolean waitUntilExistsAndClickYesIfAny(int timeout) {
		try {
			// will only wait for 5 seconds cause, cause normally, it's very fast to pop-up if exists
			WebDriverSeleniumPageEx.waitFor(timeout);
			if (isReady() && isEnabled()) {
				log.info("find message box, click yes");
				clickYesOK();
				return true;
			}
			log.info("not find message box, directly return");
		} catch (Exception ex) {
			log.warn("fail to wait for pop up confirm dialog. skip it.", ex);
		}
		
		return false;
	}

	public boolean waitUntilExistsAndClickNoIfAny(int timeout) {
		try {
			// will only wait for 5 seconds cause, cause normally, it's very fast to pop-up if exists
			WebDriverSeleniumPageEx.waitFor(timeout);
			if (isReady() && isEnabled()) {
				log.info("find message box, click no");
				clickNoCancel();
				return true;
			}
			log.info("not find message box, directly return");
		} catch (Exception ex) {
			log.warn("fail to wait for pop up confirm dialog. skip it.", ex);
		}
		
		return false;
	}

}
