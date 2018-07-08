package com.alfainmo.extra;

@SuppressWarnings("serial")
public class AlfaException extends Exception {

    public AlfaException() {
    }

    public AlfaException(String message) {
        super(message);
    }

    public AlfaException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlfaException(Throwable cause) {
        super(cause);
    }
}
