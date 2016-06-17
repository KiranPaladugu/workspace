/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.dot.xml.parsing.tech;

import java.io.IOException;
import java.util.*;

public class TagHandlerHelper {
    /**
     * 
     */
    private boolean lazyLoad = true;
    private String fileName = "TagHandlerProperties.properties";
    private Map<String, Object> handlers = new HashMap<String, Object>();
    private Properties tagHandlers = new Properties();

    public TagHandlerHelper() {
        init();
    }

    public void setLazyLoad(boolean value) {
        this.lazyLoad = value;
    }

    private void init() {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            tagHandlers.load(loader.getResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public AbstractSimpleTagHandler getTagHandler(String tagName, String uri) {
        tagHandlers.getProperty(tagName);
        return null;

    }

    public AbstractSimpleTagHandler getTagHanlder(String tagName) {
        String value = tagHandlers.getProperty(tagName);
        if (value != null && value.length() > 0) {
            Object instance = null;
            if (lazyLoad) {
                instance = getFromLocalLoad(tagName);
            }
            if (instance != null) {
                return (AbstractSimpleTagHandler) instance;
            } else {
                ClassLoader loader = Thread.currentThread().getContextClassLoader();
                try {
                    Class<?> loadedClass = loader.loadClass(value);
                    instance = loadedClass.newInstance();
                    handlers.put(tagName, instance);
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            if (instance != null && instance instanceof AbstractSimpleTagHandler) {
                return (AbstractSimpleTagHandler) instance;
            } else {
                log("%s is not instance of %s", instance.getClass().getSimpleName(),
                        AbstractSimpleTagHandler.class.getSimpleName());
            }
        }
        return null;
    }

    /**
     * @return
     */
    private Object getFromLocalLoad(String tagName) {
        return handlers.get(tagName);
    }

    public void log(String message) {
        System.out.println(message);
    }

    public void log(String format, Object... objects) {
        System.out.println(String.format(format, objects));
    }

}
