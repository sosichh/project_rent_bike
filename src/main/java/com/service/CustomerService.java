package com.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.domain.Bike;
import com.domain.Customer;
import com.exceptions.BikeNotFoundException;
import com.exceptions.CustomerNotFoundException;
import com.exceptions.CustomerSaveException;
import com.exceptions.CustomerUpdateException;
import com.repository.CustomerRepository;

public class CustomerService {
    private final CustomerRepository repository;
    private final BikeService bikeService;

    public CustomerService() throws IOException {
        repository = new CustomerRepository();
        bikeService = new BikeService();
    }

    public Customer save(Customer customer) throws IOException, CustomerSaveException {
        if (customer == null) {
            throw new CustomerSaveException("Customer cannot be null");
        }

        String name = customer.getName();

        if (name == null || name.trim().isEmpty()) {
            throw new CustomerSaveException("Customer's name cannot be empty");
        }

        customer.setActive(true);
        return repository.save(customer);
    }
    
    public List<Customer> getAllActiveCustomers() throws IOException {
        return repository.findAll()
                .stream()
                .filter(Customer::isActive)
                .collect(Collectors.toList());
    }

    public Customer getActiveCustomerById(int id) throws IOException, CustomerNotFoundException {
        Customer customer = repository.findById(id);
        
        if (customer == null || !customer.isActive()) {
            throw new CustomerNotFoundException(id);
        }

        return customer;
    }

    public void update(Customer customer) throws CustomerUpdateException, IOException {
        if (customer == null) {
            throw new CustomerUpdateException("Customer cannot be null");
        }

        String name = customer.getName();

        if (name == null || name.trim().isEmpty()) {
            throw new CustomerUpdateException("Customer's name cannot be empty");
        }

        repository.update(customer);
    }

    public void deleteById(int id) throws IOException, CustomerNotFoundException {
        getActiveCustomerById(id).setActive(false);
    }

    public void deleteByName(String name) throws IOException {
        getAllActiveCustomers()
                .stream()
                .filter(x -> x.getName().equals(name))
                .forEach(x -> x.setActive(false));
    }

    public void restoreById(int id) throws IOException, CustomerNotFoundException {
        Customer customer = repository.findById(id);
        if (customer != null) {
            customer.setActive(true);
        } else {
            throw new CustomerNotFoundException(id);
        }
    }

    public int getActiveCustomerCount() throws IOException {
        return getAllActiveCustomers().size();
    }

    public double getCustomerCartTotalPrice(int id) throws IOException, CustomerNotFoundException {
        return getActiveCustomerById(id)
                .getBikes()
                .stream()
                .filter(Bike::isActive)
                .mapToDouble(Bike::getPrice)
                .sum();
    }

    public double getCustomerCartAveragePrice(int id) throws IOException, CustomerNotFoundException {
        return getActiveCustomerById(id)
                .getBikes()
                .stream()
                .filter(Bike::isActive)
                .mapToDouble(Bike::getPrice)
                .average()
                .orElse(0.0);
    }
    
    public void addBikeToCustomerCart(int customerId, int bikeId) throws IOException, CustomerNotFoundException, BikeNotFoundException {
        Customer customer = getActiveCustomerById(customerId);
        Bike bike = bikeService.getActiveBikeById(bikeId);
        customer.getBikes().add(bike);
    }

    public void removeBikeFromCustomerCart(int customerId, int bikeId) throws IOException, CustomerNotFoundException, BikeNotFoundException {
        Customer customer = getActiveCustomerById(customerId);
        Bike bike = bikeService.getActiveBikeById(bikeId);

        customer.getBikes().remove(bike);
    }

    public void clearCustomerCart(int id) throws IOException, CustomerNotFoundException {
        getActiveCustomerById(id).getBikes().clear();
    }
}