/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tools.helpers.watcher.file;

public interface WatcherListener<T> {
    public void watcherEvent(WatcherEvent<T> event);
}
