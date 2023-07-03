package com.example.RainRESTApp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class RESTClient {
    public static void main(String[] args) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        createSensor(restTemplate, "sensor23");
        populateRandomMeasurements(restTemplate, "sensor23");
        printMeasurements(restTemplate);
    }
    public static void createSensor(RestTemplate restTemplate, String sensorName){
        String url = "http://localhost:8080/sensors/registration";
        Map<String, String> json = new HashMap<>();
        json.put("name", sensorName);
        HttpEntity<Map<String, String>> request = new HttpEntity<>(json);
        ResponseEntity<HttpStatus> entity = restTemplate.postForEntity(url, request, HttpStatus.class);
    }
    public static void populateRandomMeasurements(RestTemplate restTemplate, String sensorName) throws JsonProcessingException {
        Random random = new Random();
        String url = "http://localhost:8080/measurements/add";
        ObjectMapper mapper = new ObjectMapper();
        for (int i = 0; i < 1000; i++){

            Map<String, Object> json = new HashMap<>();
            json.put("value", String.valueOf(random.nextFloat()));
            json.put("raining", String.valueOf(random.nextBoolean()));
            Map<String, String> sensor = new HashMap<>();
            sensor.put("name", sensorName);
            json.put("sensor", sensor);

            restTemplate.postForEntity(url, new HttpEntity<>(json), HttpStatus.class);
        }
    }
    public static void printMeasurements(RestTemplate restTemplate){
        String url = "http://localhost:8080/measurements";
        System.out.println(restTemplate.getForObject(url, String.class));
    }
}

