package com.alfainmo.extra;

@SuppressWarnings("serial")
class BadDelimeterException extends RuntimeException {

    public BadDelimeterException() {
    }

    public BadDelimeterException(String s) {
        super(s);
    }
}
