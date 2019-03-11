package com.oracle.auto.web.comp.interfaces;


public interface IExpandableComponent extends IComponent, IExpandable {
    <T> T getExpandedCompAs(Class<T> classType);
}
