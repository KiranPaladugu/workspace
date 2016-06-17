package com.tcs.pack.searchJar;

public class Message {
    public static enum MESSAGE_TYPE{
        INFO,MESSAGE,WARN,ERROR,FATAL,NOTIFICATION,EXCEPTION
    }

    private MESSAGE_TYPE messageType;
    private String message;
    private String description;
    private Object source;
    
    public String getMessageType() {
        return messageType.name();
    }
    
    public MESSAGE_TYPE getEnumMessageType() {
        return messageType;
    }
    
    public void setMessageType(MESSAGE_TYPE messageType) {
        this.messageType = messageType;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the source
     */
    public Object getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(Object source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Message [messageType=" + messageType + ", message=" + message + ", description=" + description + ", source="
                + source + "]";
    }
    
    
}
