package com.example.RainRESTApp.controllers;

import com.example.RainRESTApp.dto.SensorDTO;
import com.example.RainRESTApp.models.Sensor;
import com.example.RainRESTApp.services.SensorService;
import com.example.RainRESTApp.util.SensorNotCreatedException;
import com.example.RainRESTApp.util.SensorResponseError;
import com.example.RainRESTApp.util.SensorValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/sensors")
public class SensorController {
    private final SensorService sensorService;
    private final SensorValidator sensorValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorController(SensorService sensorService, SensorValidator sensorValidator, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
        this.modelMapper = modelMapper;
    }
    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> save(@RequestBody @Valid SensorDTO sensorDTO, BindingResult bindingResult) throws SensorNotCreatedException {
        sensorValidator.validate(sensorDTO, bindingResult);
        if (bindingResult.hasErrors()){
            StringBuilder errors = new StringBuilder();
            bindingResult.getFieldErrors()
                    .stream().forEach(
                            fieldError -> errors.append(fieldError.getDefaultMessage())
                                    .append("; ")
                    );
            throw new SensorNotCreatedException(errors.toString());
        }
        sensorService.save(convertToSensor(sensorDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }
    private Sensor convertToSensor(SensorDTO sensorDTO){
        return modelMapper.map(sensorDTO, Sensor.class);
    }
    @ExceptionHandler
    public ResponseEntity<SensorResponseError> handleException(SensorNotCreatedException e){
        SensorResponseError response = new SensorResponseError(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
