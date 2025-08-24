package com.acu.mongodb.demo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CustomerRepository extends MongoRepository<Customer, String> {

    public List<Customer> findByFirstName(String firstName);
    public List<Customer> findByLastName(String lastName);
    public List<Customer> findByCity(String city);
    public List<Customer> findByCountry(String country);

}
