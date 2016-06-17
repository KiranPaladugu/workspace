/* ********************************************************************************
 * All rights reserved.
 ******************************************************************************* */
package com.tcs.tools;

import java.io.Serializable;

public abstract class Message<T> implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public static final int REQUEST = 1;
    public static final int RESPONSE = 2;
    private int messageType;
    private T message;
    private boolean replyExpected;
    private String endOfMessageDelim;
    private String name;
    private String id;

    /**
     * 
     */
    public Message() {
    }

    public Message(T message) {
        this.message = message;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((endOfMessageDelim == null) ? 0 : endOfMessageDelim.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + messageType;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + (replyExpected ? 1231 : 1237);
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
        Message<?> other = (Message<?>) obj;
        if (endOfMessageDelim == null) {
            if (other.endOfMessageDelim != null)
                return false;
        } else if (!endOfMessageDelim.equals(other.endOfMessageDelim))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (message == null) {
            if (other.message != null)
                return false;
        } else if (!message.equals(other.message))
            return false;
        if (messageType != other.messageType)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (replyExpected != other.replyExpected)
            return false;
        return true;
    }

    public boolean isRequest() {
        return REQUEST == messageType;
    }

    public boolean isResponse() {
        return RESPONSE == messageType;
    }

    public boolean isReplyExpected() {
        return replyExpected;
    }

    public void setReplyExpected(boolean replyExpected) {
        this.replyExpected = replyExpected;
    }

    public String getEndOfMessageDelim() {
        return endOfMessageDelim;
    }

    public void setEndOfMessageDelim(String endOfMessageDelim) {
        this.endOfMessageDelim = endOfMessageDelim;
    }

    public String getName() {
        if (name == null || name.length() < 1) {
            if (isRequest()) {
                return "Request";
            } else {
                return "Response";
            }
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Message [messageType=" + messageType + ", message=" + message + ", replyExpected=" + replyExpected
                + ", endOfMessageDelim=" + endOfMessageDelim + ", name=" + name + ", id=" + id + "]";
    }

}
