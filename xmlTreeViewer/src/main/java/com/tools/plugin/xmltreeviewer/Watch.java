/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tools.plugin.xmltreeviewer;

public class Watch {

    private long startTime;
    private long endTime;
    private String name = "";

    public void startWatch(final String name) {
        startTime = System.nanoTime();
        this.name = name;
    }

    public void start() {
        startTime = System.nanoTime();
    }

    public void stop() {
        endTime = System.nanoTime();
    }

    public long getTimeTaken() {
        return endTime - startTime;
    }

    public long getTimeTakenInMillis() {
        return getTimeTaken() / 1000000;
    }

    public void stats() {
        System.out.println(String.format(">Stopwatch statastics for %s", name));
        System.out.println(String.format(">>TotalTime taken by %s is [%d] nano seconds", name, getTimeTaken()));
        System.out.println(String.format(">>TotalTime taken by %s is [%d] milli seconds", name, getTimeTakenInMillis()));
        System.out.println(String.format(">>Start time of %s is [%d]", name, startTime));
        System.out.println(String.format(">>Endtime time of %s is [%d]", name, endTime));
    }
}
