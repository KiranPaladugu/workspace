/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tool.UI;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageMap {
    private static final MessageMap MESSAGEMAP = new MessageMap();

    private Map<String, Queue<String>> messageMap = new HashMap<>();

    public static final MessageMap getMessageMap() {
        return MESSAGEMAP;
    }

    public synchronized void put(String id, String name) {
        if (messageMap.containsKey(id)) {
            messageMap.get(id).add(name);
        } else {
            Queue<String> list = new ConcurrentLinkedQueue<>();
            list.add(name);
            messageMap.put(id, list);
        }
    }

    public synchronized String getName(String id) {
        if (messageMap.containsKey(id)) {
            Queue<String> queue = messageMap.get(id);
            if (!queue.isEmpty()) {
                return queue.remove();
            }
            if (queue.isEmpty()) {
                messageMap.remove(id);
            }
        }
        return null;
    }

    public synchronized String getId(String message) {
        String value = null;
        int index = -1;
        int index2 = -1;
        index = message.indexOf("message-id");
        if (index != -1) {
            String subString = message.substring(index);
            index = subString.indexOf('\"');
            if (index != -1)
                index2 = subString.indexOf("\"", index + 1);

            if (index != -1 && index2 != -1) {
                value = subString.substring(index + 1, index2);
            }
        }
        if ("".equals(value)) {
            value = null;
        }
        return value;
    }

    public synchronized boolean isError(String message) {
        if (message != null && message.indexOf("rpc-error") != -1) {
            return true;
        }
        return false;
    }
}
