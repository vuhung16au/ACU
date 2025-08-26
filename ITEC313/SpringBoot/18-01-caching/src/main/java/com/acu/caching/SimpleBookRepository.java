package com.acu.caching;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class SimpleBookRepository implements BookRepository {

	private final Map<String, Book> books = new HashMap<>();

	public SimpleBookRepository() {
		// Initialize with the provided books
		books.put("978-0-14-318069-9", new Book("978-0-14-318069-9", "The Lucky Country"));
		books.put("978-1-4842-8761-5", new Book("978-1-4842-8761-5", "Spring Boot 3 and Spring Framework 6"));
		books.put("978-0-13-793510-9", new Book("978-0-13-793510-9", "The Art of Computer Programming, Volume 1, Fascicle 1: MMIX"));
	}

	@Override
	@Cacheable("books")
	public Book getByIsbn(String isbn) {
		simulateSlowService();
		return books.getOrDefault(isbn, new Book(isbn, "Unknown book"));
	}

	// Don't do this at home
	private void simulateSlowService() {
		try {
			long time = 3000L;
			Thread.sleep(time);
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}

}
