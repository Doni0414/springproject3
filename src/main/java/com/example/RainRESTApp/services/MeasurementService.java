package com.example.RainRESTApp.services;

import com.example.RainRESTApp.models.Measurement;
import com.example.RainRESTApp.models.Sensor;
import com.example.RainRESTApp.repositories.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SensorService sensorService;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorService sensorService) {
        this.measurementRepository = measurementRepository;
        this.sensorService = sensorService;
    }

    public List<Measurement> findAll(){
        return measurementRepository.findAll();
    }
    @Transactional
    public void save(Measurement measurement){
        enrichMeasurement(measurement);
        measurementRepository.save(measurement);
    }
    private void enrichMeasurement(Measurement measurement){
        Optional<Sensor> sensor = sensorService.findSensorByName(measurement.getSensor().getName());
        measurement.setSensor(sensor.get());
        sensor.get().getMeasurements().add(measurement);
        measurement.setCreatedAt(new Date());
    }
    public int countMeasurementsByRaining(){
        return measurementRepository.countMeasurementsByRaining(true);
    }
}
