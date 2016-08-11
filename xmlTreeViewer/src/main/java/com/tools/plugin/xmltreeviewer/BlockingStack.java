/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tools.plugin.xmltreeviewer;

import java.util.List;
import java.util.Vector;

public class BlockingStack<T> {
    private int capacity = 10;
    private int size;
    private List<T> list;

    /**
     * 
     */
    public BlockingStack() {
        list = new Vector<T>(capacity);
    }

    public BlockingStack(int capacity) {
        this.capacity = capacity;
        list = new Vector<T>(capacity);
    }

    public synchronized boolean push(T object) {
        if (size >= capacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        list.add(object);
        size++;
        notify();
        return true;
    }
    public synchronized boolean push(T object, long timeout) {
        if (size >= capacity) {
            try {
                wait(timeout);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        list.add(object);
        size++;
        notify();
        return true;
    }

    public synchronized T peek() {
        if (size <= 0) {
            return null;
        }
        return list.get(size - 1);
    }

    public synchronized T pop() {
        if (isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        size--;
        T object = list.remove(size);
        notify();
        return object;
    }
    public synchronized T pop(long timeout) {
        if (isEmpty()) {
            try {
                wait(timeout);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        size--;
        T object = list.remove(size);
        notify();
        return object;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
