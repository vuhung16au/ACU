package com.acu.caching;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CachingApplicationTests {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private CacheManager cacheManager;

	@Test
	void contextLoads() {
		// This test verifies that the Spring application context loads successfully
	}

	@Test
	void testCachingFunctionality() {
		// Clear the cache before testing
		cacheManager.getCache("books").clear();

		// First request - should hit the repository (cache miss)
		Book book1 = bookRepository.getByIsbn("978-0-14-318069-9");
		
		// Verify the book is returned correctly
		assertNotNull(book1);
		assertEquals("978-0-14-318069-9", book1.getIsbn());
		assertEquals("The Lucky Country", book1.getTitle());

		// Second request with same ISBN - should hit cache (cache hit)
		Book book2 = bookRepository.getByIsbn("978-0-14-318069-9");
		
		// Verify the same book is returned
		assertNotNull(book2);
		assertEquals(book1.getIsbn(), book2.getIsbn());
		assertEquals(book1.getTitle(), book2.getTitle());
		
		// Verify that both objects are the same instance (cached)
		assertSame(book1, book2);
	}

}
