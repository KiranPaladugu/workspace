/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.pack.dot;

import java.util.List;
import java.util.Vector;

public class Stack<T> {
    private int capacity = 10;
    private int size;
    private List<T> list;

    /**
     * 
     */
    public Stack() {
        list = new Vector<T>(capacity);
    }

    public Stack(int capacity) {
        this.capacity = capacity;
        list = new Vector<T>(capacity);
    }

    public synchronized boolean push(T object) {
        boolean flag = true;
        if (size < capacity) {
            list.add(object);
            size++;
        } else {
            flag = false;
            System.out.println("Stack Over Flow");
        }
        System.out.println("POSITION:"+size);
        return flag;
    }

    public synchronized T peek() {
        if (size <= 0) {
            return null;
        }
        return list.get(size-1);
    }

    public synchronized T pop() {
        if (size <= 0) {
            System.out.println("Stack Under flow");
            return null;
        }
        size--;
        return list.remove(size);
    }
    
    public int size(){
        return size;
    }
}
