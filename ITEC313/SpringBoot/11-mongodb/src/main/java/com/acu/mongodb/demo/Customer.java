package com.acu.mongodb.demo;

import org.springframework.data.annotation.Id;

public class Customer {

    @Id
    public String id;

    public String firstName;
    public String lastName;
    public String city;
    public String country;

    public Customer() {}

    public Customer(String firstName, String lastName, String city, String country) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.country = country;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%s, firstName='%s', lastName='%s', city='%s', country='%s']",
                id, firstName, lastName, city, country);
    }

}
