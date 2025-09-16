package com.controller;

import java.io.IOException;
import java.util.List;

import com.domain.Customer;
import com.exceptions.BikeNotFoundException;
import com.exceptions.CustomerNotFoundException;
import com.exceptions.CustomerSaveException;
import com.exceptions.CustomerUpdateException;
import com.service.CustomerService;

public class CustomerController {
    
    private final CustomerService service;

    public CustomerController() throws IOException{
        service = new CustomerService();
    }

    public Customer save(String name) throws IOException, CustomerSaveException{
        Customer customer = new Customer(name);

        return service.save(customer);
    }

    public List<Customer> getAllActiveCustomers() throws IOException{
        return service.getAllActiveCustomers();
    }

    public Customer getActiveCustomerById(int id) throws IOException, CustomerNotFoundException{
        return service.getActiveCustomerById(id);
    }

    public void update(int id, String name) throws IOException, CustomerUpdateException{
        Customer customer = new Customer(id, name);

        service.update(customer);
    }

    public void deleteById(int id) throws IOException, CustomerNotFoundException{
        service.deleteById(id);
    }

    public void deleteByName(String name) throws IOException{
        service.deleteByName(name);
    }
    public void restoreById(int id) throws IOException, CustomerNotFoundException{
        service.restoreById(id);
    }

    public int getActiveCustomerCount() throws IOException{
        return service.getActiveCustomerCount();
    }

    public double getCustomerCartTotalPrice(int id) throws IOException, CustomerNotFoundException{
        return service.getCustomerCartTotalPrice(id);
    }

    public double getCustomerCartAveragePricee(int id) throws IOException, CustomerNotFoundException{
        return service.getCustomerCartAveragePrice(id);
    }

    public void addBikeToCustomerCart(int customerId, int bikeId) throws IOException, CustomerNotFoundException, BikeNotFoundException{
        service.addBikeToCustomerCart(customerId, bikeId);
    }

    public void removeBikeFromCustomerCart(int customerId, int bikeId) throws IOException, CustomerNotFoundException, BikeNotFoundException {
        service.removeBikeFromCustomerCart(customerId, bikeId);
    }

    public void clearCustomerCart(int id) throws IOException, CustomerNotFoundException{
        service.clearCustomerCart(id);
    }

}
