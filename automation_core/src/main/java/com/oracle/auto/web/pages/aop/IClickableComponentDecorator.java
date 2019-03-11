package com.oracle.auto.web.pages.aop;


import com.oracle.auto.web.comp.interfaces.IClickableComponent;

public interface IClickableComponentDecorator extends IClickableComponent {

	void setComponent(IClickableComponent comp);

}
