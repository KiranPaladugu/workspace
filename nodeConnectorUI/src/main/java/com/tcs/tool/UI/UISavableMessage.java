/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tool.UI;

import java.io.Serializable;

import com.tcs.tools.Message;

public class UISavableMessage<T> extends Message<T> implements Serializable {
/**
     * 
     */
    private static final long serialVersionUID = 1L;
    //    private boolean isSave;
    private boolean autoSend;
    private boolean isFirst;
    private boolean autoClose;
   

    public synchronized boolean isAutoSend() {
        return autoSend;
    }
    public synchronized void setAutoSend(boolean autoSend) {
        this.autoSend = autoSend;
    }
    public synchronized boolean isFirst() {
        return isFirst;
    }
    public synchronized void setFirst(boolean isFirst) {
        this.isFirst = isFirst;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (autoSend ? 1231 : 1237);
        result = prime * result + (isFirst ? 1231 : 1237);
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        UISavableMessage<?> other = (UISavableMessage<?>) obj;
        if (autoSend != other.autoSend)
            return false;
        if (isFirst != other.isFirst)
            return false;
        return super.equals(obj);
    }
    public boolean isAutoClose() {
        return autoClose;
    }
    public void setAutoClose(boolean autoClose) {
        this.autoClose = autoClose;
    }
    
    

}
