/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.application.pluign;

import java.lang.reflect.InvocationTargetException;
import java.rmi.NoSuchObjectException;

import com.tcs.application.Subscriber;
import com.tcs.application.SubscriptionEvent;

public class PluginStarter implements Subscriber {

    /*
     * (non-Javadoc)
     * 
     * @see com.tcs.application.Subscriber#onSubscriptionEvent(com.tcs.application.SubscriptionEvent)
     */
    @Override
    public void onSubscriptionEvent(SubscriptionEvent event) {

    }

    /**
     * 
     */
    public void startPluin(PluginDataObject pluginDataObject) {
        String className = pluginDataObject.getClassName();
        String method = pluginDataObject.getActivatorMethod();
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try {
            Class<?> loadedClass = classLoader.loadClass(className);
            Object obj = loadedClass.newInstance();
            MethodInvoker invoker = new MethodInvoker();
            invoker.invokeTheMethod(obj, method);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchObjectException
                | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

}
