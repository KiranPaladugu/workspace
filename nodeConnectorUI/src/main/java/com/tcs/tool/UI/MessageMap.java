/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tool.UI;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageMap {
	private static final MessageMap MESSAGEMAP = new MessageMap();

	private final Map<String, Queue<String>> messageMap = new HashMap<>();

	public static final MessageMap getMessageMap() {
		return MESSAGEMAP;
	}

	public synchronized void put(final String id, final String name) {
		if (messageMap.containsKey(id)) {
			messageMap.get(id).add(name);
		} else {
			final Queue<String> list = new ConcurrentLinkedQueue<>();
			list.add(name);
			messageMap.put(id, list);
		}
	}

	public synchronized String getName(final String id) {
		if (messageMap.containsKey(id)) {
			final Queue<String> queue = messageMap.get(id);
			if (!queue.isEmpty()) {
				return queue.remove();
			}
			if (queue.isEmpty()) {
				messageMap.remove(id);
			}
		}
		return null;
	}

	public synchronized String getId(final String message) {
		String value = null;
		int index = -1;
		int index2 = -1;
		index = message.indexOf("message-id");
		if (index != -1) {
			final String subString = message.substring(index);
			index = subString.indexOf('\"');
			if (index != -1) index2 = subString.indexOf("\"", index + 1);

			if (index != -1 && index2 != -1) {
				value = subString.substring(index + 1, index2);
			}
		}
		if ("".equals(value)) {
			value = null;
		}
		return value;
	}

	public synchronized boolean isError(final String message) {
		if (message != null && message.indexOf("rpc-error") != -1) {
			return true;
		}
		return false;
	}
}
