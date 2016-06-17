/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tool.UI;

import java.awt.GridLayout;

import javax.swing.JSeparator;

public class SessionView extends ControlPanel {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private HistoryView history;
    private ConnectionView connection;
    private RequestControlView reqControl;
    private MessageDisplayView display;
    private ConnectionManger connectionManager;
    @SuppressWarnings("unused")
    private NewCoustomMessageView newCoustomMessageView;

    public SessionView() {
        setExpandPolicy(BOTH);
        this.setLayout(new GridLayout());
        setConnectionManager(new ConnectionManger());
        reqControl = new RequestControlView();
        display = new MessageDisplayView();
        history = new HistoryView();
        connection = new ConnectionView();
        newCoustomMessageView= new NewCoustomMessageView();

        //        LayoutUtils.arrangeComponantsInRow(connection,reqControl,display).setExpandPolicy(BOTH);
        //        LayoutUtils.arrangeComponantsInColoumn(history,LayoutUtils.arrangeComponantsInRow(connection,reqControl,display).setExpandPolicy(BOTH));
        this.add(LayoutUtils.arrangeComponantsInColoumn(history,
                LayoutUtils.arrangeComponantsInRow(connection, reqControl,new JSeparator(), display).setExpandPolicy(BOTH)));
        //        LayoutUtils.arrangeCom
        //        this.add)
    }

    public ConnectionManger getConnectionManager() {
        return connectionManager;
    }

    public void setConnectionManager(ConnectionManger connectionManager) {
        this.connectionManager = connectionManager;
    }
}
