package com.oracle.auto.web.pages.object;


public interface IFormSubmitPage {
	boolean isValidForSubmit();
	PageBase submitExpectingFailure();
	PageBase submit();
}
