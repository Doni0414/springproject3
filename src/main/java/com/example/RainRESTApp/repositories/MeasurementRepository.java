package com.example.RainRESTApp.repositories;

import com.example.RainRESTApp.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    int countMeasurementsByRaining(boolean raining);
}
