package com.acu.javafx.storelist;

import java.util.Collections;
import java.util.LinkedList;
import java.util.StringJoiner;

/**
 * Simple wrapper around LinkedList<Integer> providing
 * unique insertion and utility operations used by the UI.
 */
public class NumberList {
    private final LinkedList<Integer> data = new LinkedList<>();

    /** Add only if the number is not already present. */
    public void addUnique(int value) {
        if (!data.contains(value)) {
            data.add(value);
        }
    }

    /** Sort ascending. */
    public void sort() {
        Collections.sort(data);
    }

    /** Shuffle randomly. */
    public void shuffle() {
        Collections.shuffle(data);
    }

    /** Reverse order. */
    public void reverse() {
        Collections.reverse(data);
    }

    /** Text view used by UI and tests: numbers separated by a single space. */
    public String joinWithSpaces() {
        StringJoiner joiner = new StringJoiner(" ");
        for (Integer v : data) joiner.add(String.valueOf(v));
        return joiner.toString();
    }

    /** Expose size for tests. */
    public int size() { return data.size(); }

    /** Clear all numbers (used in tests). */
    public void clear() { data.clear(); }
}


