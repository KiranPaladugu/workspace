/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.bccl;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostIpPrinter {
    public static void main(String args[]){
        try {
            System.out.println(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
