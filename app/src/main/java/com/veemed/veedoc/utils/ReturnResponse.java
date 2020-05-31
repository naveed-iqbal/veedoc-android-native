package com.veemed.veedoc.utils;

public class ReturnResponse<T> {

    private String message;
    private boolean valid = false;
    private T returnObject;

    public ReturnResponse() {

    }

    public ReturnResponse(String message, boolean valid) {
        this.message = message;
        this.valid = valid;
    }

    public ReturnResponse(T t, String message, boolean valid) {
        this(message, valid);
        this.returnObject = t;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public T getReturnObject() {
        return returnObject;
    }

    public void setReturnObject(T returnObject) {
        this.returnObject = returnObject;
    }
}
