
package com.pack.dot;

import java.util.List;
import java.util.Vector;

public class UnlimitedStack<T> {

    private List<T> list;
    private int size;

    public UnlimitedStack() {
        list = new Vector<T>();
    }

    public synchronized boolean push(T object) {
        boolean flag = false;
        if (list.add(object)) {
            size++;
            flag = true;
        }
        return flag;
    }

    public synchronized T peek() {
        if (size <= 0) {
            return null;
        }
        return list.get(size - 1);
    }

    public synchronized T pop() {
        if (size <= 0) {
            return null;
        }
        size--;
        return list.remove(size);
    }

    public int size() {
        return size;
    }
}
