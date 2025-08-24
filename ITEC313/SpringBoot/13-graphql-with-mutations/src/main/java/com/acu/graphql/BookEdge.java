package com.acu.graphql;

public class BookEdge {
    private String cursor;
    private Book node;

    public BookEdge(String cursor, Book node) {
        this.cursor = cursor;
        this.node = node;
    }

    public String getCursor() {
        return cursor;
    }

    public void setCursor(String cursor) {
        this.cursor = cursor;
    }

    public Book getNode() {
        return node;
    }

    public void setNode(Book node) {
        this.node = node;
    }
}
