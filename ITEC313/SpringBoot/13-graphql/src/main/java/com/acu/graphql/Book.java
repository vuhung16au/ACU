package com.acu.graphql;

import java.util.Arrays;
import java.util.List;

public record Book(String id, String name, int pageCount, String authorId) {

    private static List<Book> books = Arrays.asList(
            new Book("book-1", "The Lucky Country", 300, "author-1"),
            new Book("book-2", "The Magic Pudding: Being The Adventures of Bunyip Bluegum and his friends Bill Barnacle and Sam Sawnoff", 250, "author-2")
    );

    public static Book getById(String id) {
        return books.stream()
                .filter(book -> book.id().equals(id))
                .findFirst()
                .orElse(null);
    }
}
