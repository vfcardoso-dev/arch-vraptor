package com.viniciuscardoso.arch.vraptor.exception;

/**
 * Project: arch
 * User: Vinicius Cardoso
 * Date: 28/03/14
 * Time: 14:38
 */
public class ControllerException extends ArchException {

    protected ControllerException(String message) {
        super(message);
    }

    protected ControllerException(String message, Throwable cause) {
        super(message, cause);
    }
}
