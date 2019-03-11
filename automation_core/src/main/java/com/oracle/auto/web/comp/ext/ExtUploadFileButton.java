package com.oracle.auto.web.comp.ext;

import com.oracle.auto.web.exceptions.ComponentSetPropertyException;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.List;

// xtype: filefield
public class ExtUploadFileButton extends ExtComponentBase {
	private static Logger log = Logger.getLogger(ExtUploadFileButton.class);
	public ExtUploadFileButton(WebDriverSeleniumPageEx page, String locator) {
		super(page, locator);
	}

	public void uploadFile(String path) {
		waitForReadyEnabled();

		if (! new File(path).exists()) {
			log.warn("try to upload file, but fail doesn't exists: " + path);
		}
		
		String inputId = getFileInputId();
		try {
			page.sendkeys(inputId, path);
		} catch (Exception ex) {
			throw new ComponentSetPropertyException(page, locator, inputId + " key as " + path, ex);
		}
	}

	public void uploadFiles(List<String> paths) {
		waitForReadyEnabled();

		String inputId = getFileInputId();
		String keys = "";
		for (String path : paths) {
			if (!keys.isEmpty()) keys += "\n";
			keys += path;
		}
		try {
			page.sendkeys(inputId, keys);
		} catch (Exception ex) {
			throw new ComponentSetPropertyException(page,locator, inputId + " key as " + keys, ex);
		}
	}

	protected String getFileInputId() {
		return getMethodProp(".fileInputEl.id").str();
	}
}
