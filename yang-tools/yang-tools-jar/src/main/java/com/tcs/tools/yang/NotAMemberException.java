package com.tcs.tools.yang;

public class NotAMemberException extends RuntimeException{

    /**
     * 
     */
    public NotAMemberException() {
        super();
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public NotAMemberException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @param message
     * @param cause
     */
    public NotAMemberException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public NotAMemberException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public NotAMemberException(Throwable cause) {
        super(cause);
    }
    
}
