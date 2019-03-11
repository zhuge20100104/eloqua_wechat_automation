package com.oracle.auto.web.pages.object;

import com.google.gson.annotations.Expose;
import com.oracle.auto.web.comp.ComponentAdaptorBase;
import com.oracle.auto.web.comp.interfaces.*;
import com.oracle.auto.web.exceptions.PageNoItemException;
import com.oracle.auto.web.pages.WebDriverSeleniumPageEx;
import com.oracle.auto.web.pages.aop.IClickableComponentDecorator;
import com.oracle.auto.web.utility.FieldHelper;
import com.oracle.auto.web.utility.ResourceLoaderCross;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.ReflectionUtils;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public class PageObjectBase<E extends PageObjectBase<?>> extends PageBase {
    private static Logger log = Logger.getLogger(PageObjectBase.class);
    protected ApplicationContext appContext = null;
    private Class<? extends PageObjectBase<?>> defaultPageObject = PageObjectAdaptor.class;

    protected String pageJsonName = "";

    //
    // public method: constructor
    //
    public PageObjectBase(String pageJsonName) {
        super();
        this.pageJsonName = pageJsonName;
    }

    protected void setDefaultPageObject(Class<? extends PageObjectBase<?>> theClass) {
        defaultPageObject = theClass;
    }

    protected void loadJson(String pageJsonName) {
        appContext = new ClassPathXmlApplicationContext(ResourceLoaderCross.instance().getFileArray("*.comp.xml"));
        String jsonValue = PageObjectJsonParser.getJsonStringFromResourcePage(pageJsonName);

        // load property
        FieldHelper.setFields(jsonValue, this, Expose.class);

        // register components
        loadComponents(pageJsonName);
    }

    protected void loadComponents(String pageJsonName) {
        Map<String, ComponentData> compList = PageObjectJsonParser.loadComponentsDeep(pageJsonName);
        for (ComponentData data : compList.values()) {
            IComponent comp = createCompFromData(data);
            super.registerComp(data.name, comp);
        }
    }

    protected IComponent createCompFromData(ComponentData data) {
        return ComponentLoader.instance().getCompByData(data, page);
    }

    @Override
    public void loadPageConfig() {
        super.loadPageConfig();

        if (!pageJsonName.isEmpty())
            loadJson(pageJsonName);
    }

    // for inherited
    protected PageObjectBase() {
        super();
    }


    //
    // public method: get/set
    //
    //
    // public method: IComponent
    //
    public IComponent getComp(String compName) {
        return getCompAs(compName, IComponent.class);
    }


    public <T> T getCompAs(String compName, Class<T> classType) {
        IComponent obj = compList.get(compName);
        if (obj == null)
            throw new PageNoItemException(page, getClass().getName(), "Component Name [" + compName + "], Component Type [" + classType.toString() + "]", "There's no such component in this page.");

        if (!classType.isInstance(obj))
            throw new PageNoItemException(page, getClass().getName(), "Component Name [" + compName + "], Component Type [" + classType.toString() + "], expecting type: " + obj.getClass().toString(), "Component type is not as expected in this page.");

        return classType.cast(obj);
    }

    public boolean isComponentEnabled(String compName) {
        // wait for 2 second to check because, if after typing something then check it immediately, it might be still disabled
        WebDriverSeleniumPageEx.waitFor(2);

        return getComp(compName).isEnabled();
    }

    //
    // public method: IClickable
    //
    public void click(String compName) {
        IClickable comp = getCompAs(compName, IClickable.class);
        comp.click();
    }

    public void click(String compName, String clickHandler) {
        IClickableComponent comp = getCompAs(compName, IClickableComponent.class);
        IClickableComponentDecorator handler = (IClickableComponentDecorator) appContext.getBean(clickHandler);
        handler.setComponent(comp);
        handler.click();
    }

    public PageObjectBase<?> clickToPage(String compName, String pageJsonName) {
        click(compName);

        return pageAs(defaultPageObject, pageJsonName);
    }

    public PageObjectBase<?> clickToPage(String compName, String clickHandler, String pageJsonName) {
        click(compName, clickHandler);

        return pageAs(defaultPageObject, pageJsonName);
    }

    public PageObjectBase<?> clickToPage(String compName, Class<? extends PageObjectBase<?>> theClass, Object... initOrgs) {
        click(compName);

        return pageAs(theClass, initOrgs);
    }

    public PageObjectBase<?> clickToPage(String compName, String clickHandler, Class<? extends PageObjectBase<?>> theClass, Object... initOrgs) {
        click(compName, clickHandler);

        return pageAs(theClass, initOrgs);
    }

    //
    // public method: IValuable
    //
    @SuppressWarnings("unchecked")
    public E setValue(Map<String, String> values) {
        for (String compName : values.keySet()) {
            setValue(compName, values.get(compName));
        }

        return (E) this;
    }

    @SuppressWarnings("unchecked")
    public E setValue(String compName, String value) {
        IValuable comp = getCompAs(compName, IValuable.class);
        comp.setValue(value);

        return (E) this;
    }

    // need to pass in value's keys, then return values inside the map
    @SuppressWarnings("unchecked")
    public E getValue(Map<String, String> values) {
        for (String compName : compList.keySet()) {
            values.put(compName, getValue(compName));
        }

        return (E) this;
    }

    // need to pass in value's keys, then return values inside the map
    public String getValue(String compName) {
        IValueDisplay comp = getCompAs(compName, IValueDisplay.class);
        return comp.getValue();
    }

    //
    // public method: IMultipleValue
    //

    //
    // public method: IComponentList
    //
    public <T> T getCompFromListAs(String listName, String itemValue, Class<T> clazz) {
        IComponentList list = getCompAs(listName, IComponentList.class);
        T item = list.getCompAs(itemValue, clazz);
        if (item == null)
            throw new PageNoItemException(page, getClass().getName(), "List Name [" + listName + "], Item Value: " + itemValue + ", Component Type:" + clazz.toString(), "There's no such component item on comp list.");

        return item;
    }

    public <T> T getExpandedCompFromListAs(String listName, String itemValue, Class<T> clazz) {
        IComponentList list = getCompAs(listName, IComponentList.class);
        IExpandableComponent item = list.getCompAs(itemValue, IExpandableComponent.class);
        if (item == null)
            throw new PageNoItemException(page, getClass().getName(), "List Name [" + listName + "], Item Value: " + itemValue + ", Component Type:" + clazz.toString(), "There's no such component item on comp list.");

        T ret = item.getExpandedCompAs(clazz);
        if (ret == null)
            throw new PageNoItemException(page, getClass().getName(), "List Name [" + listName + "], Item Value: " + itemValue + ", Component Type:" + clazz.toString(), "There's no expanded item of the expanded comp.");

        return ret;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private Method getMethod() {
        Method method = null;
        try {
            method = ComponentAdaptorBase.class.getMethod("setPage", WebDriverSeleniumPageEx.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return method;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void injectPageToChildComponents(Object compObject) {
        ReflectionUtils.doWithFields(compObject.getClass(), new ReflectionUtils.FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                Method m = getMethod();
                try {
                    ReflectionUtils.makeAccessible(field);
                    Object object = new PropertyDescriptor(field.getName(), compObject.getClass()).
                            getReadMethod().invoke(compObject);
                    ReflectionUtils.invokeMethod(m, object, page);
                    injectPageToChildComponents(object);

                } catch (Exception e) {
                    log.info(e.getMessage());
                }

            }
        }, new ReflectionUtils.FieldFilter() {
            @Override
            public boolean matches(Field field) {
                if (field.getGenericType().getClass().equals(ParameterizedTypeImpl.class)) {
                    return false;
                }
                return ComponentAdaptorBase.class.isAssignableFrom((Class<?>) field.getGenericType());
            }
        });
    }


}
