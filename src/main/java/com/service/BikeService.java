package com.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.domain.Bike;
import com.exceptions.BikeNotFoundException;
import com.exceptions.BikeSaveException;
import com.exceptions.BikeUpdateException;
import com.repository.BikeRepository;

public class BikeService {
    private final BikeRepository repository;

    public BikeService() throws IOException, IOException {
        repository = new BikeRepository();
    }

    public Bike save(Bike bike) throws BikeSaveException, IOException {
        if (bike == null) {
            throw new BikeSaveException("Bike cannot be null");
        }

        String title = bike.getTitle();
        if (title == null || title.trim().isEmpty()) {
            throw new BikeSaveException("Bike's title cannot be empty");
        }

        if (bike.getPrice() <= 0) {
            throw new BikeSaveException("Bike's prive must be more than zero");
        }

        bike.setActive(true);
        return repository.save(bike);
    }

    public List<Bike> getAllActiveBikes() throws IOException {
        return repository.findAll()
                .stream()
                .filter(Bike::isActive)
                .collect(Collectors.toList());
    }

    public Bike getActiveBikeById(int id) throws IOException, BikeNotFoundException {
        Bike bike = repository.findById(id);

        if (bike == null || !bike.isActive()) {
            throw new BikeNotFoundException(id);
        }
        return bike;
    }

    public void update(Bike bike) throws BikeUpdateException, IOException {
        if (bike == null) {
            throw new BikeUpdateException("Bike cannot be null");
        }

        if (bike.getPrice() <= 0) {
            throw new BikeUpdateException("Bike's price must be more than zero");
        }

        repository.update(bike);
    }

    public void deleteById(int id) throws IOException, BikeNotFoundException {
        getActiveBikeById(id).setActive(false);
    }

    public void deleteByTitle(String title) throws IOException {
        getAllActiveBikes()
                .stream()
                .filter(x -> x.getTitle().equals(title))
                .forEach(x -> x.setActive(false));
    }

    public void restoreById(int id) throws IOException, BikeNotFoundException {
        Bike bike = repository.findById(id);

        if (bike != null) {
            bike.setActive(true);
        } else {
            throw new BikeNotFoundException(id);
        }
    }

   
    public int getActiveBikesCount() throws IOException {
        return getAllActiveBikes().size();
    }

    public double getActiveBikesTotalCost() throws IOException {
        return getAllActiveBikes()
                .stream()
                .mapToDouble(Bike::getPrice)
                .sum();
    }
    public double getActiveBikesAveragePrice() throws IOException {
        int bikeCount = getActiveBikesCount();

        if (bikeCount == 0) {
            return 0.0;
        }

        return getActiveBikesTotalCost()/bikeCount;
    }
}