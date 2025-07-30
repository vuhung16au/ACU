package com.acu.javafx.geometricobjectcomparator;

import java.util.Comparator;

public class GeometricObjectComparator
    implements Comparator<GeometricObject>, java.io.Serializable {
  public int compare(GeometricObject o1, GeometricObject o2) {
    return o1.getArea() > o2.getArea() ? 
        1 : o1.getArea() == o2.getArea() ? 0 : -1;
  }
} 