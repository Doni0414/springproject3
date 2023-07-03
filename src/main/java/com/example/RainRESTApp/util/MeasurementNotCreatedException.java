package com.example.RainRESTApp.util;

import com.example.RainRESTApp.models.Measurement;

public class MeasurementNotCreatedException extends Exception{
    public MeasurementNotCreatedException(String message){
        super(message);
    }
}
