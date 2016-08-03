/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.practice.mbeans;

import java.lang.management.ManagementFactory;

import javax.management.*;

public class MBeanTester extends Thread {
    private static SampleMBeanImpl sample = new SampleMBeanImpl();

    public MBeanTester() {
        this.start();
        final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        try {
            final ObjectName mbName = new ObjectName("SampleMBean: type=" + SampleMBean.class.getSimpleName());
            mbs.registerMBean(sample, mbName);
        } catch (MalformedObjectNameException | InstanceAlreadyExistsException | MBeanRegistrationException
                | NotCompliantMBeanException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String args[]){
        MBeanTester tester = new MBeanTester();        
    }
}
