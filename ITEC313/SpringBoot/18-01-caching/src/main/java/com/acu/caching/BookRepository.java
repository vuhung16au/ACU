package com.acu.caching;

public interface BookRepository {

	Book getByIsbn(String isbn);

}
