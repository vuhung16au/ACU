package com.acu.car.management.persistence.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Car entity representing vehicles in the management system
 */
public class Car {
    // Car identifier
    Long Id;
    // Car manufacturer (e.g., Toyota, Ford)
    String make;
    // Car model name (e.g., Camry, Mustang)
    String model;
    // Manufacturing year
    Integer year;
    // Car color
    String color;
    // Current mileage
    Double mileage;
    // Car category (Sedan, SUV, etc.)
    String category;
    // Collection of car components/parts
    final Set<Component> components = new HashSet<>();

    /**
     * Default constructor
     */
    public Car() {
    }

    /**
     * Constructor with all fields including ID
     */
    public Car(Long Id, String make, String model, Integer year, String color, Double mileage, String category) {
        this.Id = Id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.mileage = mileage;
        this.category = category;
    }

    /**
     * Constructor for new cars (without ID)
     */
    public Car(String make, String model, Integer year, String color, Double mileage, String category) {
        this(null, make, model, year, color, mileage, category);
    }

    // Getters and setters
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Returns the set of components associated with this car
     */
    public Set<Component> getComponents() {
        return components;
    }

    /**
     * Adds a component to this car and sets the bidirectional relationship
     */
    public void addComponent(Component component) {
        components.add(component);
        component.setCar(this);
    }

    /**
     * Removes a component from this car and clears the relationship
     */
    public void removeComponent(Component component) {
        components.remove(component);
        component.setCar(null);
    }

    /**
     * Cars are considered equal if they have the same make, model, year, color, mileage, and category
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Car car = (Car) o;
        return Objects.equals(make, car.make) && Objects.equals(model, car.model) && Objects.equals(year, car.year) && Objects.equals(color, car.color) && Objects.equals(mileage, car.mileage) && Objects.equals(category, car.category);
    }

    /**
     * Hash code based on car properties
     */
    @Override
    public int hashCode() {
        return Objects.hash(make, model, year, color, mileage, category);
    }

    @Override
    public String toString() {
        return "Car{" + "Id=" + Id + ", make='" + make + '\'' + ", model='" + model + '\'' + ", year=" + year + ", color='" + color + '\'' + ", mileage=" + mileage + ", category='" + category + '\'' + ", components=" + components + '}';
    }
}
