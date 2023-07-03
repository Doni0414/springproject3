package com.example.RainRESTApp.util;

import com.example.RainRESTApp.dto.SensorDTO;
import com.example.RainRESTApp.models.Sensor;
import com.example.RainRESTApp.services.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class SensorValidator implements Validator {
    private final SensorService sensorService;

    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensor = (SensorDTO) target;
        Optional<Sensor> optionalSensor = sensorService.findSensorByName(sensor.getName());

        if (!optionalSensor.isEmpty())
            errors.rejectValue("name", "", "Sensor with this name is present");
    }
}
