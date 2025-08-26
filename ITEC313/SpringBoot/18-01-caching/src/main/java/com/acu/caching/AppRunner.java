package com.acu.caching;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(AppRunner.class);

	private final BookRepository bookRepository;

	public AppRunner(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info(".... Fetching books");
		
		// First requests - will be slow (cache miss)
		logger.info("isbn-978-0-14-318069-9 -->" + bookRepository.getByIsbn("978-0-14-318069-9"));
		logger.info("isbn-978-1-4842-8761-5 -->" + bookRepository.getByIsbn("978-1-4842-8761-5"));
		logger.info("isbn-978-0-13-793510-9 -->" + bookRepository.getByIsbn("978-0-13-793510-9"));
		
		// Second requests - will be fast (cache hit)
		logger.info("isbn-978-0-14-318069-9 -->" + bookRepository.getByIsbn("978-0-14-318069-9"));
		logger.info("isbn-978-1-4842-8761-5 -->" + bookRepository.getByIsbn("978-1-4842-8761-5"));
		logger.info("isbn-978-0-13-793510-9 -->" + bookRepository.getByIsbn("978-0-13-793510-9"));
		
		// Additional requests to show more cache hits
		logger.info("isbn-978-0-14-318069-9 -->" + bookRepository.getByIsbn("978-0-14-318069-9"));
		logger.info("isbn-978-1-4842-8761-5 -->" + bookRepository.getByIsbn("978-1-4842-8761-5"));
	}

}
