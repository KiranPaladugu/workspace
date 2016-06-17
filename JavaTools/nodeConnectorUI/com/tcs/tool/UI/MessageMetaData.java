/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tool.UI;

import java.io.Serializable;

public class MessageMetaData implements Cloneable,Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String messageName;
    private String messageId;
    private boolean isVisible;
    private boolean isAutoSend;
    private boolean enableSend;
    public synchronized String getMessageName() {
        return messageName;
    }
    public synchronized void setMessageName(String messageName) {
        this.messageName = messageName;
    }
    public synchronized String getMessageId() {
        return messageId;
    }
    public synchronized void setMessageId(String messageId) {
        this.messageId = messageId;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((messageId == null) ? 0 : messageId.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MessageMetaData other = (MessageMetaData) obj;
        if (messageId == null) {
            if (other.messageId != null)
                return false;
        } else if (!messageId.equals(other.messageId))
            return false;
        return true;
    } 
    
    public String toString(){
        if(this.messageName!=null){
            return messageName;
        }
        return messageId;
    }
    public boolean isVisible() {
        return isVisible;
    }
    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }
    public boolean isAutoSend() {
        return isAutoSend;
    }
    public void setAutoSend(boolean isAutoSend) {
        this.isAutoSend = isAutoSend;
    }
    public boolean isEnableSend() {
        return enableSend;
    }
    public void setEnableSend(boolean enableSend) {
        this.enableSend = enableSend;
    }
    
}
