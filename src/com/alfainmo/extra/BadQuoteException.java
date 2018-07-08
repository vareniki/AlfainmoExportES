package com.alfainmo.extra;

public class BadQuoteException extends RuntimeException {

    public BadQuoteException() {
    }

    public BadQuoteException(String s) {
        super(s);
    }
}
