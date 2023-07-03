package com.example.RainRESTApp.util;

import com.example.RainRESTApp.dto.MeasurementDTO;
import com.example.RainRESTApp.models.Measurement;
import com.example.RainRESTApp.models.Sensor;
import com.example.RainRESTApp.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class MeasurementValidator implements Validator {
    private final SensorRepository sensorRepository;

    @Autowired
    public MeasurementValidator(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeasurementDTO measurement = (MeasurementDTO) target;

        Optional<Sensor> optionalSensor = sensorRepository.findSensorByName(measurement.getSensor().getName());

        if (optionalSensor.isEmpty())
            errors.rejectValue("sensor", "", "Sensor with this name is not exist: " + measurement.getSensor().getName());
    }
}
