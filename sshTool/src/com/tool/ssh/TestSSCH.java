/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tool.ssh;

import javax.swing.JOptionPane;

import com.jcraft.jsch.*;

public class TestSSCH {
    public static void main(String args[]){
        JSch sch = new JSch();
        String userName = "ekirpal";
        String password = "kiran";
        String host = "10.126.139.36";
        int port =22;
        
        try {
            Session session = sch.getSession(userName, host, port);
//            session.setPassword(password);;
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect(30000);            
            Channel channel = session.openChannel("shell");
            channel.setInputStream(System.in);
            channel.setOutputStream(System.err);            
            channel.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }
}
