package com.acu.javafx.linkedlist;

import java.util.Iterator;

public class MyLinkedList<E> implements MyList<E>, Iterable<E> {
  protected Node<E> head, tail;
  protected int size = 0;
  
  public MyLinkedList() {
  }

  public MyLinkedList(E[] objects) {
    for (int i = 0; i < objects.length; i++)
      add(objects[i]); 
  }

  public E getFirst() {
    if (size == 0) {
      return null;
    }
    else {
      return head.element;
    }
  }

  public E getLast() {
    if (size == 0) {
      return null;
    }
    else {
      return tail.element;
    }
  }

  public void addFirst(E e) {
    Node<E> newNode = new Node<>(e);
    newNode.next = head;
    head = newNode;
    size++;

    if (tail == null)
      tail = head;
  }

  public void addLast(E e) {
    Node<E> newNode = new Node<>(e);

    if (tail == null) {
      head = tail = newNode;
    }
    else {
      tail.next = newNode;
      tail = newNode;
    }

    size++;
  }

  @Override
  public void add(E e) {
    addLast(e);
  }

  @Override
  public void add(int index, E e) {
    if (index == 0) {
      addFirst(e);
    }
    else if (index >= size) {
      addLast(e);
    }
    else {
      Node<E> current = head;
      for (int i = 1; i < index; i++) {
        current = current.next;
      }
      Node<E> temp = current.next;
      current.next = new Node<>(e);
      (current.next).next = temp;
      size++;
    }
  }

  public E removeFirst() {
    if (size == 0) {
      return null;
    }
    else {
      E temp = head.element;
      head = head.next;
      size--;
      if (head == null) {
        tail = null;
      }
      return temp;
    }
  }

  public E removeLast() {
    if (size == 0) {
      return null;
    }
    else if (size == 1) {
      E temp = head.element;
      head = tail = null;
      size = 0;
      return temp;
    }
    else {
      Node<E> current = head;

      for (int i = 0; i < size - 2; i++) {
        current = current.next;
      }

      E temp = tail.element;
      tail = current;
      tail.next = null;
      size--;
      return temp;
    }
  }

  @Override
  public E remove(int index) {   
    if (index < 0 || index >= size) {
      return null;
    }
    else if (index == 0) {
      return removeFirst();
    }
    else if (index == size - 1) {
      return removeLast();
    }
    else {
      Node<E> previous = head;

      for (int i = 1; i < index; i++) {
        previous = previous.next;
      }

      Node<E> current = previous.next;
      previous.next = current.next;
      size--;
      return current.element;
    }
  }

  @Override
  public String toString() {
    if (size == 0) {
      return "[]";
    }
    
    StringBuilder result = new StringBuilder("[");

    Node<E> current = head;
    for (int i = 0; i < size; i++) {
      result.append(current.element);
      current = current.next;
      if (current != null) {
        result.append(", ");
      }
      else {
        result.append("]");
      }
    }

    return result.toString();
  }

  @Override
  public void clear() {
    size = 0;
    head = tail = null;
  }

  @Override
  public boolean contains(Object e) {
    Node<E> current = head;
    while (current != null) {
      if (current.element.equals(e)) {
        return true;
      }
      current = current.next;
    }
    return false;
  }

  @Override
  public E get(int index) {
    if (index < 0 || index >= size) {
      return null;
    }
    Node<E> current = head;
    for (int i = 0; i < index; i++) {
      current = current.next;
    }
    return current.element;
  }

  @Override
  public int indexOf(Object e) {
    Node<E> current = head;
    int index = 0;
    while (current != null) {
      if (current.element.equals(e)) {
        return index;
      }
      current = current.next;
      index++;
    }
    return -1;
  }

  @Override
  public int lastIndexOf(E e) {
    Node<E> current = head;
    int index = 0;
    int lastIndex = -1;
    while (current != null) {
      if (current.element.equals(e)) {
        lastIndex = index;
      }
      current = current.next;
      index++;
    }
    return lastIndex;
  }

  @Override
  public E set(int index, E e) {
    if (index < 0 || index >= size) {
      return null;
    }
    Node<E> current = head;
    for (int i = 0; i < index; i++) {
      current = current.next;
    }
    E oldElement = current.element;
    current.element = e;
    return oldElement;
  }

  @Override
  public Iterator<E> iterator() {
    return new LinkedListIterator();
  }
  
  private class LinkedListIterator implements Iterator<E> {
    private Node<E> current = head;
    
    @Override
    public boolean hasNext() {
      return (current != null);
    }

    @Override
    public E next() {
      E e = current.element;
      current = current.next;
      return e;
    }

    @Override
    public void remove() {
      // Implementation left as exercise
    }
  }
  
  protected static class Node<E> {
    E element;
    Node<E> next;

    public Node(E element) {
      this.element = element;
    }
  }
  
  @Override
  public int size() {
    return size;
  }
}
