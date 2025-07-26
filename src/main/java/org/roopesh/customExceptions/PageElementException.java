package org.roopesh.customExceptions;

public class PageElementException extends RuntimeException{

    public PageElementException() {
        super();
    }


    public PageElementException(String message) {
        super(message);
    }


    public PageElementException(String message, Throwable cause) {
        super(message, cause);
    }


    public PageElementException(Throwable cause) {
        super(cause);
    }

}
