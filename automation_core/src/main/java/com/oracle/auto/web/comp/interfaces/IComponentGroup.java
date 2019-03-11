package com.oracle.auto.web.comp.interfaces;

import java.util.List;

public interface IComponentGroup extends IComponent {
    void registerComp(IComponent... comps);

    void registerComp(String compName, IComponent comp);

    IComponent getComp(String compName);

    List<IComponent> getComps();

    <T> T getCompAs(String compName, Class<T> classType);
}
