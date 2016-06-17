/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.ssh.netconf.connector.node;

public class Test {
    public static void main(String[] args){
        String user = "test";
        SshSessionProvider provider = new SshSessionProvider(830, "10.170.117.194",user,user);
        provider.setSubSystem("netconf-ecim");
        provider.openSession();
        provider.closeSession();
    }
}
