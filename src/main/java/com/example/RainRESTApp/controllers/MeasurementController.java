package com.example.RainRESTApp.controllers;

import com.example.RainRESTApp.dto.MeasurementDTO;
import com.example.RainRESTApp.models.Measurement;
import com.example.RainRESTApp.services.MeasurementService;
import com.example.RainRESTApp.services.SensorService;
import com.example.RainRESTApp.util.MeasurementNotCreatedException;
import com.example.RainRESTApp.util.MeasurementResponseError;
import com.example.RainRESTApp.util.MeasurementValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;
    private final MeasurementValidator measurementValidator;

    @Autowired
    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper, MeasurementValidator measurementValidator) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
        this.measurementValidator = measurementValidator;
    }

    @GetMapping()
    public List<MeasurementDTO> getAllMeasurements(){
        return measurementService.findAll().stream()
                .map(this::convertToMeasurementDTO)
                .collect(Collectors.toList());
    }
    @GetMapping("/rainyDaysCount")
    public ResponseEntity<Integer> getRainyDaysCount(){
        return new ResponseEntity<>(measurementService.countMeasurementsByRaining(), HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MeasurementDTO measurementDTO,
                                             BindingResult bindingResult) throws MeasurementNotCreatedException {
        measurementValidator.validate(measurementDTO, bindingResult);
        if (bindingResult.hasErrors()){
            StringBuilder errors = new StringBuilder();
            bindingResult.getFieldErrors()
                    .stream().forEach(
                            fieldError -> errors.append(fieldError.getDefaultMessage())
                                    .append("; ")
                    );
            throw new MeasurementNotCreatedException(errors.toString());
        }
        measurementService.save(convertToMeasurement(measurementDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }
    private Measurement convertToMeasurement(MeasurementDTO measurementDTO){
        return modelMapper.map(measurementDTO, Measurement.class);
    }
    private MeasurementDTO convertToMeasurementDTO(Measurement measurement){
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
    @ExceptionHandler
    public ResponseEntity<MeasurementResponseError> handleException(MeasurementNotCreatedException e){
        MeasurementResponseError response = new MeasurementResponseError(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
