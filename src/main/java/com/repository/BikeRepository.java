package com.repository;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.domain.Bike;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

public class BikeRepository {

    private final File database;    
    private final ObjectMapper mapper;  
    private int maxId;         

    
    
    public BikeRepository() throws IOException {
        database = new File("database\\bike.txt");
        mapper = new ObjectMapper();

        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        
        List<Bike> bikes = findAll(); 
        if (!bikes.isEmpty()) {         
            Bike lastBike = bikes.get(bikes.size() - 1); 
            maxId = lastBike.getId();     
        }
    }

    
    public Bike save(Bike bike) throws IOException {
        bike.setId(++maxId);           
        List<Bike> bikes = findAll();  
        bikes.add(bike);               
        mapper.writeValue(database, bikes);  
        return bike;
    }

    
    public List<Bike> findAll() throws IOException { 
        try {
            
            Bike[] bikes = mapper.readValue(database, Bike[].class); 
            return new ArrayList<>(Arrays.asList(bikes)); 
            
        } catch (MismatchedInputException e) {
            return new ArrayList<>();
        }
    }

    
    public Bike findById(int id) throws IOException { 
        return findAll()   
                .stream()  
                .filter(x -> x.getId() == id) 
                .findFirst()  
                .orElse(null); 
    }

    
    
    public void update(Bike bike) throws IOException {
        int id = bike.getId(); 
        double newPrice = bike.getPrice(); 
                                          

        List<Bike> bikes = findAll(); 
        bikes
                .stream()  
                .filter(x -> x.getId() == id) 
                .forEach(x -> x.setPrice(newPrice));
           
          

        mapper.writeValue(database, bikes); 

    }

        
    public void deleteById(int id) throws IOException { 
        List<Bike> bikes = findAll(); 
        bikes.removeIf(x -> x.getId() == id); 
        mapper.writeValue(database,  bikes); 
    }

}
