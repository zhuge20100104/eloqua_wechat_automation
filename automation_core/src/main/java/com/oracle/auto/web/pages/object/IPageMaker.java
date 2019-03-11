package com.oracle.auto.web.pages.object;


public interface IPageMaker {
	<T extends PageBase> T pageAs(Class<T> pageClass, Object... initArgs);
}
