package com.oracle.auto.mobile.screen;

import com.oracle.auto.mobile.components.MobileComponentBase;
import com.oracle.auto.mobile.driver.MobileDriverEx;
import org.apache.log4j.Logger;
import org.springframework.util.ReflectionUtils;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ScreenObjectBase<E extends ScreenObjectBase<?>> extends ScreenBase {

    private static Logger log = Logger.getLogger(ScreenObjectBase.class);

    public ScreenObjectBase() { super(); }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private Method getMethod() {
        Method method = null;
        try {
            method = MobileComponentBase.class.getMethod("setMobileDriverEx", MobileDriverEx.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return method;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected void injectMobileDriverToChildComponents(Object mobileObject) {
        ReflectionUtils.doWithFields(mobileObject.getClass(), new ReflectionUtils.FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                Method m = getMethod();
                try {
                    ReflectionUtils.makeAccessible(field);
                    Object object = new PropertyDescriptor(field.getName(), mobileObject.getClass()).getReadMethod().invoke(mobileObject);
                    ReflectionUtils.invokeMethod(m, object, mobileDriverEx);
                    injectMobileDriverToChildComponents(object);
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
                return MobileComponentBase.class.isAssignableFrom((Class<?>) field.getGenericType());
            }
        });
    }
}