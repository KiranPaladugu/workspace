/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tool.UI;

import com.tcs.application.*;

public class NodeConnectorPlugin implements Plugin, Subscriber {

    /*
     * (non-Javadoc)
     * 
     * @see com.tcs.application.Subscriber#onSubscriptionEvent(com.tcs.application.SubscriptionEvent)
     */
    private boolean started;
    private String plugId;
    private String name;
    private String identifier;

    @Override
    public void onSubscriptionEvent(SubscriptionEvent event) {

    }

    public void startUIPlugin() {
        if (!started) {
            MainWindow window = new MainWindow();
            window.requestFocus();
            window.setVisible(true);
           /* StartUi starter = new StartUi(window);
            try {
                SwingUtilities.invokeAndWait(starter);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            started = true;
            Application.getSubscriptionManager().notifySubscriber(Application.PLUGIN_STATED, this);
        }
    }
    
    public void stopPlugin(){
        
    }

    /* (non-Javadoc)
     * @see com.tcs.application.Plugin#setPlugId(java.lang.String)
     */
    @Override
    public void setPlugId(String id) {
        this.plugId = id;
    }
    
    public String getPlugId(){
        return this.plugId;
    }

    /* (non-Javadoc)
     * @see com.tcs.application.Plugin#setName(java.lang.String)
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /* (non-Javadoc)
     * @see com.tcs.application.Plugin#getName()
     */
    @Override
    public String getName() {
        return name;
    }

    /* (non-Javadoc)
     * @see com.tcs.application.Plugin#setIdentifier(java.lang.String)
     */
    @Override
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /* (non-Javadoc)
     * @see com.tcs.application.Plugin#getIdentifier()
     */
    @Override
    public String getIdentifier() {
        return identifier;
    }

}
