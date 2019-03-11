package com.oracle.auto.web.comp;

import com.google.gson.annotations.Expose;
import com.oracle.auto.web.comp.interfaces.IComponent;
import com.oracle.auto.web.comp.interfaces.IComponentGroup;
import com.oracle.auto.web.pages.object.ComponentData;
import com.oracle.auto.web.pages.object.ComponentLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public abstract class ComponentGroupBase extends ComponentAdaptorBase implements IComponentGroup {
    protected SortedMap<String, IComponent> compList = new TreeMap<String, IComponent>();
    private int nameStart = 10000;

    @Expose
    protected List<ComponentData> elements = null;

    public ComponentGroupBase() { }

    public ComponentGroupBase(String locator) {
        setLocator(locator);
    }

    @Override
    public void setConfig(String jsonValue) {
        super.setConfig(jsonValue);

        // now load element components
        if (elements == null) return;

        for (ComponentData data : elements) {
            data.replaceJsonFirstLevel("{parent}", locator);
            IComponent comp = ComponentLoader.instance().getCompByData(data, page);
            registerComp(data.name, comp);
        }
    }

    @Override
    public void registerComp(IComponent... comps) {
        for (IComponent comp : comps)
            compList.put("Component " + (nameStart++), comp);
    }

    @Override
    public void registerComp(String compName, IComponent comp) {
        compList.put(compName, comp);
    }

    @Override
    public IComponent getComp(String compName) {
        return getCompAs(compName, IComponent.class);
    }

    @Override
    public <T> T getCompAs(String compName, Class<T> classType) {
        IComponent obj = compList.get(compName);
        if (obj == null)
            throw new RuntimeException("cannot find component: " + compName);

        if (!classType.isInstance(obj))
            throw new RuntimeException("component is not as expected type: " + compName + " expected type: " + classType + " actual type: " + obj.toString());

        return classType.cast(obj);
    }

    @Override
    public List<IComponent> getComps() {
        List<IComponent> ret = new ArrayList<>();
        ret.addAll(compList.values());

        return ret;
    }
}
