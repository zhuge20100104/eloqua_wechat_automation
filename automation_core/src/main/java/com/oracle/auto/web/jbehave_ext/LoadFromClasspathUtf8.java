package com.oracle.auto.web.jbehave_ext;

import org.apache.commons.io.IOUtils;
import org.jbehave.core.io.InvalidStoryResource;
import org.jbehave.core.io.LoadFromClasspath;

import java.io.IOException;
import java.io.InputStream;

public class LoadFromClasspathUtf8 extends LoadFromClasspath {

    public LoadFromClasspathUtf8(Class<?> loadFromClass) {
        super(loadFromClass);
    }

    public LoadFromClasspathUtf8(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    public String loadResourceAsText(String resourcePath) {
        InputStream stream = resourceAsStream(resourcePath);
        try {
            return IOUtils.toString(stream, "UTF-8");
        } catch (IOException e) {
            throw new InvalidStoryResource(resourcePath, stream, e);
        }
    }
}