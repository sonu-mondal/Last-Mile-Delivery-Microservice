package com.vit.lmd.driverMS.Exception;

public class ResourceNotFoundException extends Exception {
    //we can add extra properties as per our requirement
    //now to manage this exception we have to create a global exception handler
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException() {
        super("Resource not found !! ");
    }
}
