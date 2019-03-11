package com.oracle.auto.web.jbehave_ext.test;

import org.jbehave.core.steps.ParameterConverters;

import java.lang.reflect.Type;

/**
 * Created by zhous5 on 2017/6/15.
 */
public class TraderConverter implements ParameterConverters.ParameterConverter {
    private TraderPersister traderPersister = new TraderPersister();

    @Override
    public boolean accept(Type type) {
        if (type instanceof Class<?>) {
            Class classType = (Class<?>) type;
            return Trader.class.isAssignableFrom(classType);
        }
        return false;
    }

    @Override
    public Object convertValue(String value, Type type) {
        return traderPersister.retrieveTrder(value);
    }
}
