package com.acu.javafx.probing;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * LinkedList implementation based on the original JavaScript version.
 * This class provides a simple linked list data structure for use in hash table implementations.
 */
public class LinkedList<E> implements Iterable<E> {
    private Node<E> head;
    private Node<E> tail;
    private int size;

    /**
     * Node class for the linked list.
     */
    private static class Node<E> {
        E element;
        Node<E> next;

        Node(E element) {
            this.element = element;
            this.next = null;
        }
    }

    /**
     * Constructor for LinkedList.
     */
    public LinkedList() {
        this.size = 0;
        this.head = null;
        this.tail = null;
    }

    /**
     * Add an element to the end of the list.
     */
    public void add(E e) {
        Node<E> node = new Node<>(e);
        
        if (this.tail == null) {
            this.head = this.tail = node;
        } else {
            this.tail.next = node;
            this.tail = node;
        }
        
        this.size++;
    }

    /**
     * Insert the element to the beginning of the list.
     */
    public void addFirst(E e) {
        Node<E> node = new Node<>(e);
        node.next = this.head;
        this.head = node;
        this.size++;
        
        if (this.tail == null) {
            this.tail = this.head;
        }
    }

    /**
     * Insert the element at the specified index.
     */
    public void insert(int index, E e) {
        if (index == 0) {
            addFirst(e);
        } else if (index >= this.size) {
            add(e);
        } else {
            Node<E> current = this.head;
            for (int i = 1; i < index; i++) {
                current = current.next;
            }
            Node<E> temp = current.next;
            current.next = new Node<>(e);
            current.next.next = temp;
            this.size++;
        }
    }

    /**
     * Remove the first element in the list.
     */
    public E removeFirst() {
        if (this.size == 0) {
            return null;
        } else {
            Node<E> temp = this.head;
            this.head = this.head.next;
            this.size--;
            if (this.head == null) {
                this.tail = null;
            }
            return temp.element;
        }
    }

    /**
     * Remove the last element in the list.
     */
    public E removeLast() {
        if (this.size == 0) {
            return null;
        } else if (this.size == 1) {
            Node<E> temp = this.head;
            this.head = this.tail = null;
            this.size--;
            return temp.element;
        } else {
            Node<E> current = this.head;
            while (current.next != this.tail) {
                current = current.next;
            }
            Node<E> temp = this.tail;
            this.tail = current;
            this.tail.next = null;
            this.size--;
            return temp.element;
        }
    }

    /**
     * Remove the specified element.
     */
    public boolean remove(E e) {
        int index = indexOf(e);
        if (index < 0) {
            return false;
        } else {
            removeAt(index);
            return true;
        }
    }

    /**
     * Remove the element at the specified index.
     */
    public E removeAt(int index) {
        if (index < 0 || index >= this.size) {
            return null; // Out of range
        } else if (index == 0) {
            return removeFirst();
        } else if (index == this.size - 1) {
            return removeLast();
        } else {
            Node<E> previous = this.head;
            for (int i = 1; i < index; i++) {
                previous = previous.next;
            }
            Node<E> current = previous.next;
            previous.next = current.next;
            this.size--;
            return current.element;
        }
    }

    /**
     * Get the size of the list.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Clear all elements from the list.
     */
    public void clear() {
        this.head = this.tail = null;
        this.size = 0;
    }

    /**
     * Check if the list is empty.
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Find the index of the specified element.
     */
    public int indexOf(E e) {
        Node<E> current = this.head;
        int index = 0;
        while (current != null && !current.element.equals(e)) {
            current = current.next;
            index++;
        }
        
        if (index >= this.getSize()) {
            return -1;
        } else {
            return index;
        }
    }

    /**
     * Get the element at the specified index.
     */
    public E get(int index) {
        if (index < 0 || index > this.size - 1) {
            return null;
        }
        
        Node<E> current = this.head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.element;
    }

    /**
     * Check if the list contains the specified element.
     */
    public boolean contains(E e) {
        return indexOf(e) >= 0;
    }

    /**
     * Get the last index of the specified element.
     */
    public int lastIndexOf(E e) {
        // To be implemented
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        Node<E> current = this.head;
        while (current != null) {
            if (current != this.head) {
                s.append(", ");
            }
            s.append(current.element);
            current = current.next;
        }
        return s.append("]").toString();
    }

    @Override
    public Iterator<E> iterator() {
        return new LinkedListIterator();
    }

    /**
     * Iterator implementation for the linked list.
     */
    private class LinkedListIterator implements Iterator<E> {
        private Node<E> current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            E element = current.element;
            current = current.next;
            return element;
        }
    }
} 