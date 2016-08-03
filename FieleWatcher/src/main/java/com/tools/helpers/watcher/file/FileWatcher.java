/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tools.helpers.watcher.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.WatchEvent.Kind;

public class FileWatcher {
    private WatcherListener<File> listener;
    private File fileToWatch;
    private boolean started;

    public FileWatcher() {
    }

    public void setWatcherListener(WatcherListener<File> listener) {
        this.listener = listener;
        doWatching();
    }

    public void startWatching(String fileName) {
        if (fileName == null) {
            return;
        }
        fileToWatch = new File(fileName);
        doWatching();

    }
    
    public Object getWatchingSource(){
        return fileToWatch;
    }
    
    private void doWatching(){
        if(!started){
            start();
        }
    }

    /**
     * 
     */
    private void start() {
        if(listener!=null && fileToWatch!=null){
            try {
                WatchService service = FileSystems.getDefault().newWatchService();
                Path path = Paths.get(fileToWatch.getAbsolutePath());
                path.register(service,StandardWatchEventKinds.ENTRY_CREATE,StandardWatchEventKinds.ENTRY_DELETE,StandardWatchEventKinds.ENTRY_MODIFY);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    protected class WathingThread extends Thread {
        public void run(){
            
        }
    }

    public void watch(String fileName) {
        File file = new File(fileName);

    }

}
