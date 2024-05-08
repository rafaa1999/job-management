package com.rafaa.carpark.exception;

public class CarParkNotFoundException extends RuntimeException{
    public CarParkNotFoundException(String message){
       super(message);
    }
}
