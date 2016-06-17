/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.application.pluign;

import java.io.File;

import com.tcs.application.*;

public class PluginLoader implements Subscriber {

    private static final PluginLoader pluginLoader = new PluginLoader();
    private String pluginPath = System.getProperty("user.home") + File.separator + ".tcs" + File.separator + "plugin";

    /**
     * 
     */
    public PluginLoader() {
    }

    public void start() {
        Application.getSubscriptionManager().subscribe(this, Application.LOAD_PLUGINS,Application.START_PLUGIN);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.tcs.application.Subscriber#onSubscriptionEvent(com.tcs.application.SubscriptionEvent)
     */
    @Override
    public synchronized void onSubscriptionEvent(SubscriptionEvent event) {
        switch (event.getEvent()) {
        case Application.LOAD_PLUGINS:
            startLoadingPluigns();
            break;
        case Application.START_PLUGIN :
            PluginStarter starter = new PluginStarter();
            try {
                Application.getPluginLoaderQueue().insert(starter);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            starter.startPluin((PluginDataObject) event.getData());
            break;
        default:
            break;
        }
    }

    private synchronized void startLoadingPluigns() {
        File file = new File(pluginPath);
        JarLoader loader = new JarLoader();
        if (file.exists() && file.isDirectory() && file.canRead()) {
            File[] files = file.listFiles();
            for (File readFile : files) {
                PluginIdentifier identifier = new PluginIdentifier();
                PluginDataObject pluginDataObject = identifier.identify(readFile);
                if (pluginDataObject != null) {
                    loader.loadJar(readFile);
                    loader.getPluginDetails();
                    System.out.println("Found PluginDataObject with name:"+readFile.getAbsolutePath());
                    Application.getSubscriptionManager().notifySubscriber(Application.PLUGIN_FOUND, this, pluginDataObject);
                }
                
            }

        }else{
            file.mkdirs();
            System.out.println("PluginDataObject folder doesn't exist, now created");
        }
    }

    public static PluginLoader getPluginloader() {
        return pluginLoader;
    }

}
