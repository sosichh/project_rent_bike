package com.controller;

import java.io.IOException;
import java.util.List;

import com.exceptions.BikeNotFoundException;
import com.exceptions.BikeSaveException;
import com.exceptions.BikeUpdateException;
import com.domain.BikeType;
import com.domain.Bike;
import com.service.BikeService;

public class BikeController {
    
    private final BikeService service;

    public BikeController() throws IOException{
        service = new BikeService();
    }

    public Bike save(String title, BikeType type, double price) throws IOException, BikeSaveException{
        Bike bike = new Bike(title, type, price);
        return service.save(bike);
    }

    public List<Bike> getAllActiveBikes() throws IOException{
        return service.getAllActiveBikes();
    }   

    public Bike getActiveBikeById(int id) throws IOException, BikeNotFoundException{
        return service.getActiveBikeById(id);
    }

    public void update(int id, double price) throws IOException, BikeUpdateException{
        Bike bike = new Bike(id, price);
        service.update(bike);
    }

    public void deleteById(int id) throws IOException, BikeNotFoundException{
        service.deleteById(id);
    }

    public void deleteByTitle(String title) throws IOException{
        service.deleteByTitle(title);
    }

    public void restoreById(int id) throws IOException, BikeNotFoundException{
        service.restoreById(id);
    }

    public int getActiveBikesCount() throws IOException{
        return service.getActiveBikesCount();
    }

    public double getActiveBikesTotalCost() throws IOException{
        return service.getActiveBikesTotalCost();
    }

    public double getActiveBikesAveragePrice() throws IOException{
        return service.getActiveBikesAveragePrice();
    }  
}
