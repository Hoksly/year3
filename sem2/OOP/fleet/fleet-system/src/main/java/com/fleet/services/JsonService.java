package com.fleet.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fleet.models.Vehicle;

import java.util.List;

public class JsonService {
    public String convertToJson(List<Vehicle> vehicles) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(vehicles);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "[]";
        }
    }
}