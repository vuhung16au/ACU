package com.acu.car.management.persistence.model;

import java.util.Objects;

/**
 * Component entity representing car parts/components
 */
public class Component {
    // Component identifier
    Long Id;
    // Associated car
    Car car;
    // Unique component code/identifier
    String componentCode;
    // Component description
    String description;
    // Component cost
    Double cost;

    /**
     * Default constructor
     */
    public Component() {
    }

    /**
     * Constructor with all fields including ID
     */
    public Component(Long id, Car car, String componentCode, String description, Double cost) {
        this.Id = id;
        this.car = car;
        this.componentCode = componentCode;
        this.description = description;
        this.cost = cost;
    }

    /**
     * Constructor for new components (without ID)
     */
    public Component(Car car, String componentCode, String description, Double cost) {
        this(null, car, componentCode, description, cost);
    }

    // Getters and setters
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getComponentCode() {
        return componentCode;
    }

    public void setComponentCode(String componentCode) {
        this.componentCode = componentCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    /**
     * Components are considered equal if they have the same code, description, and cost
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Component component = (Component) o;
        return Objects.equals(componentCode, component.componentCode) && Objects.equals(description, component.description) && Objects.equals(cost, component.cost);
    }

    /**
     * Hash code based on component properties
     */
    @Override
    public int hashCode() {
        return Objects.hash(componentCode, description, cost);
    }

    @Override
    public String toString() {
        return "Component{" + "Id=" + Id + ", componentCode='" + componentCode + '\'' + ", description='" + description + '\'' + ", cost=" + cost + '}';
    }
}
