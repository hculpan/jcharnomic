package com.charnomic.jcharnomic;

/**
 * Created by harryculpan on 4/16/17.
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String msg) {
        super(msg);
    }

    public UnauthorizedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
