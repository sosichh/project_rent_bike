package com.exceptions;

public class BikeNotFoundException extends Exception{

    public BikeNotFoundException(int id) {
        super(String.format("Bike with identifier %d not found", id));
    }
    
}