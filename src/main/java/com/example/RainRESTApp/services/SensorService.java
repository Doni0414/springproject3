package com.example.RainRESTApp.services;

import com.example.RainRESTApp.models.Sensor;
import com.example.RainRESTApp.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SensorService {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }
    public Optional<Sensor> findSensorByName(String name){
        return sensorRepository.findSensorByName(name);
    }
    @Transactional
    public void save(Sensor sensor){
        sensorRepository.save(sensor);
    }
}
