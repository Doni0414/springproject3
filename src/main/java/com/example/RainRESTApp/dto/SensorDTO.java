package com.example.RainRESTApp.dto;

import javax.validation.constraints.NotEmpty;

public class SensorDTO {
    @NotEmpty(message = "Sensor name should not be empty")
    private String name;

    public SensorDTO(){

    }
    public SensorDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
