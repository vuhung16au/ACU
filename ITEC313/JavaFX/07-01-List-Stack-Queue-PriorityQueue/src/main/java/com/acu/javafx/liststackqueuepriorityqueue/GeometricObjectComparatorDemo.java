package com.acu.javafx.liststackqueuepriorityqueue;

import java.util.*;

public class GeometricObjectComparatorDemo {
    public static void main(String[] args) {
        Circle circle1 = new Circle(5, "red", true);
        Circle circle2 = new Circle(3, "blue", false);
        Rectangle rectangle1 = new Rectangle(4, 6, "green", true);
        Rectangle rectangle2 = new Rectangle(2, 8, "yellow", false);

        List<GeometricObject> objects = new ArrayList<>();
        objects.add(circle1);
        objects.add(circle2);
        objects.add(rectangle1);
        objects.add(rectangle2);

        System.out.println("Before sorting:");
        for (GeometricObject obj : objects) {
            System.out.println(obj + " - Area: " + obj.getArea());
        }

        // Sort using the comparator
        Collections.sort(objects, new GeometricObjectComparator());

        System.out.println("\nAfter sorting by area:");
        for (GeometricObject obj : objects) {
            System.out.println(obj + " - Area: " + obj.getArea());
        }
    }
} 