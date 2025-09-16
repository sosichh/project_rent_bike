package com.exceptions;

public class CustomerNotFoundException extends Exception{

    public CustomerNotFoundException(int id) {
        super(String.format("Customer with identifier %d not found", id));
    }
    
}
