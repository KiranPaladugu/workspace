/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.application;

import java.util.concurrent.atomic.AtomicBoolean;

import com.tcs.application.pluign.*;

public class Application implements Subscriber {

    private static BlockingQueue<Object> pluingLoader = new BlockingQueue<>(1);
    public static final String EXIT = "exit";
    public static final String START = "start";
    public static final String STARTED = "started";
    public static final String JAR_LOAD_SUCCESS = "JarLoadSucess";
    public static final String LOAD_JAR = "loadJar";
    public static final String JAR_LOAD_FAIL = "JarLoadFail";
    public static final String LOAD_PLUGINS = "loadPlugins";
    public static final String PLUGIN_FOUND = "plugin_found";
    public static final String START_PLUGIN = "startPlugin";
    public static final String PLUGIN_STATED = "pluginStarted";

    private static Application application = new Application();
    private AtomicBoolean started = new AtomicBoolean(false);

    private Application() {
        Application.getSubscriptionManager().subscribe(this, START, STARTED, EXIT,PLUGIN_STATED);
    }

    public static final Context getApplicationContext() {
        return Context.getApplicationContext();
    }

    public static final Container getApplicationContainer() {
        return Container.getApplicationContainer();
    }

    public static final Configuration getApplicationConfiguration() {
        return Configuration.getApplicationConfiguration();
    }

    public static final SubscriptionChannel getSubscriptionManager() {
        return SingleChannelSubscriptionManger.getSubscriptionManger();
    }
    
    public static final PluginContainer getPluginContainer(){
        return PluginContainer.gePluginContainer();
    }

    public static final Application getApplication() {
        return application;
    }

    public static final PluginLoader getPluginLoader() {
        return PluginLoader.getPluginloader();
    }

    public synchronized Application startApplication() {
       /* if (!started.get()) {
            MainWindow window = new MainWindow();
            StartUi starter = new StartUi(window);
            try {
                SwingUtilities.invokeAndWait(starter);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
        getSubscriptionManager().notifySubscriber(START, this);
        return this;
    }

    public synchronized void shutDown() {
        if (started.get()) {

        }
    }

    public static void main(String[] args) {
        Application.getApplication().startApplication();
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public synchronized void onSubscriptionEvent(SubscriptionEvent event) {
        switch (event.getEvent()) {
        case START:
            this.started.set(true);
            getPluginContainer().load();
            getPluginLoader().start();
            getSubscriptionManager().notifySubscriber(LOAD_PLUGINS);
            break;
        case EXIT:
            System.out.println("Exiting Application..");
            Application.getSubscriptionManager().printStats();
            System.exit(0);
        case PLUGIN_STATED:
            try {
                Object obj = pluingLoader.peek();
                if(obj != null && obj instanceof PluginStarter){
                    pluingLoader.remove();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        default:
            break;
        }

    }

    /**
     * @return
     */
    public static BlockingQueue<Object> getPluginLoaderQueue() {
        return pluingLoader;
    }
}
