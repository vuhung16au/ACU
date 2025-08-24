package com.acu.hibernate.demo;

import com.acu.hibernate.entity.Author;
import com.acu.hibernate.entity.Book;
import com.acu.hibernate.entity.Tag;
import com.acu.hibernate.repository.AuthorRepository;
import com.acu.hibernate.repository.BookRepository;
import com.acu.hibernate.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Hibernate ORM Demonstration Class
 * 
 * This class demonstrates various Hibernate concepts:
 * 1. Entity Creation and Persistence
 * 2. One-to-Many Relationships (Author -> Books)
 * 3. Many-to-Many Relationships (Books <-> Tags)
 * 4. Basic CRUD Operations
 * 5. Query Methods
 * 6. Lazy vs Eager Loading
 * 7. Cascade Operations
 */
@Component
@Profile("!test") // Don't run during tests
public class HibernateDemo implements CommandLineRunner {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TagRepository tagRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("🚀 HIBERNATE ORM DEMONSTRATION");
        System.out.println("=".repeat(80));

        // Clear existing data
        clearData();

        // Run demonstrations
        demonstrateEntityCreation();
        demonstrateOneToManyRelationship();
        demonstrateManyToManyRelationship();
        demonstrateCRUDOperations();
        demonstrateQueryMethods();
        demonstrateLazyLoading();
        demonstrateCascadeOperations();

        System.out.println("\n" + "=".repeat(80));
        System.out.println("✅ HIBERNATE DEMONSTRATION COMPLETED SUCCESSFULLY!");
        System.out.println("=".repeat(80));
    }

    /**
     * Demonstration 1: Basic Entity Creation and Persistence
     */
    private void demonstrateEntityCreation() {
        System.out.println("\n📝 DEMONSTRATION 1: Entity Creation and Persistence");
        System.out.println("-".repeat(60));

        // Create and save an author
        Author author = new Author();
        author.setName("J.K. Rowling");
        author.setEmail("jk.rowling.demo@example.com");
        author.setBiography("British author best known for the Harry Potter series");
        author.setBirthDate(LocalDate.of(1965, 7, 31));

        Author savedAuthor = authorRepository.save(author);
        System.out.println("✅ Created and saved author: " + savedAuthor.getName() + " (ID: " + savedAuthor.getId() + ")");

        // Create and save a tag
        Tag tag = new Tag();
        tag.setName("Fantasy");
        tag.setDescription("Fantasy literature genre");

        Tag savedTag = tagRepository.save(tag);
        System.out.println("✅ Created and saved tag: " + savedTag.getName() + " (ID: " + savedTag.getId() + ")");
    }

    /**
     * Demonstration 2: One-to-Many Relationship (Author -> Books)
     */
    private void demonstrateOneToManyRelationship() {
        System.out.println("\n📚 DEMONSTRATION 2: One-to-Many Relationship (Author -> Books)");
        System.out.println("-".repeat(60));

        // Find the author
        Author author = authorRepository.findByName("J.K. Rowling").orElse(null);
        if (author == null) {
            System.out.println("❌ Author not found!");
            return;
        }

        // Create books and associate with author
        Book book1 = new Book();
        book1.setTitle("Harry Potter and the Philosopher's Stone");
        book1.setDescription("The first book in the Harry Potter series");
        book1.setPublicationYear(1997);
        book1.setIsbn("978-0747532699");
        book1.setPrice(new BigDecimal("29.99"));
        book1.setPageCount(223);

        Book book2 = new Book();
        book2.setTitle("Harry Potter and the Chamber of Secrets");
        book2.setDescription("The second book in the Harry Potter series");
        book2.setPublicationYear(1998);
        book2.setIsbn("978-0747538493");
        book2.setPrice(new BigDecimal("32.99"));
        book2.setPageCount(251);

        // Use the helper method to establish the relationship
        author.addBook(book1);
        author.addBook(book2);

        // Save the author (books will be saved due to CASCADE.ALL)
        Author savedAuthor = authorRepository.save(author);
        System.out.println("✅ Created One-to-Many relationship:");
        System.out.println("   Author: " + savedAuthor.getName());
        System.out.println("   Books: " + savedAuthor.getBooks().size() + " books associated");
        
        savedAuthor.getBooks().forEach(book -> 
            System.out.println("   - " + book.getTitle() + " (ID: " + book.getId() + ")"));
    }

    /**
     * Demonstration 3: Many-to-Many Relationship (Books <-> Tags)
     */
    private void demonstrateManyToManyRelationship() {
        System.out.println("\n🏷️ DEMONSTRATION 3: Many-to-Many Relationship (Books <-> Tags)");
        System.out.println("-".repeat(60));

        // Create additional tags
        Tag fantasyTag = tagRepository.findByName("Fantasy").orElse(null);
        
        Tag adventureTag = new Tag();
        adventureTag.setName("Adventure");
        adventureTag.setDescription("Adventure literature genre");
        adventureTag = tagRepository.save(adventureTag);

        Tag youngAdultTag = new Tag();
        youngAdultTag.setName("Young Adult");
        youngAdultTag.setDescription("Young adult literature genre");
        youngAdultTag = tagRepository.save(youngAdultTag);

        // Get books and associate with tags
        List<Book> books = bookRepository.findAll();
        
        for (Book book : books) {
            // Add fantasy tag to all books
            book.addTag(fantasyTag);
            
            // Add adventure tag to first book
            if (book.getTitle().contains("Philosopher")) {
                book.addTag(adventureTag);
            }
            
            // Add young adult tag to all books
            book.addTag(youngAdultTag);
            
            bookRepository.save(book);
        }

        System.out.println("✅ Created Many-to-Many relationships:");
        System.out.println("   Books: " + books.size() + " books");
        System.out.println("   Tags: " + tagRepository.count() + " tags");
        
        books.forEach(book -> {
            System.out.println("   - " + book.getTitle() + " has " + book.getTags().size() + " tags:");
            book.getTags().forEach(tag -> System.out.println("     * " + tag.getName()));
        });
    }

    /**
     * Demonstration 4: CRUD Operations
     */
    private void demonstrateCRUDOperations() {
        System.out.println("\n🔄 DEMONSTRATION 4: CRUD Operations");
        System.out.println("-".repeat(60));

        // CREATE - Create a new author
        Author newAuthor = new Author();
        newAuthor.setName("George R.R. Martin");
        newAuthor.setEmail("grrm.demo@example.com");
        newAuthor.setBiography("American novelist and short story writer");
        newAuthor.setBirthDate(LocalDate.of(1948, 9, 20));
        
        Author createdAuthor = authorRepository.save(newAuthor);
        System.out.println("✅ CREATE: Created author: " + createdAuthor.getName());

        // READ - Find author by ID
        Optional<Author> foundAuthor = authorRepository.findById(createdAuthor.getId());
        if (foundAuthor.isPresent()) {
            System.out.println("✅ READ: Found author: " + foundAuthor.get().getName());
        }

        // UPDATE - Update author information
        createdAuthor.setBiography("American novelist best known for A Song of Ice and Fire series");
        Author updatedAuthor = authorRepository.save(createdAuthor);
        System.out.println("✅ UPDATE: Updated author biography");

        // DELETE - Delete the author
        authorRepository.deleteById(updatedAuthor.getId());
        System.out.println("✅ DELETE: Deleted author: " + updatedAuthor.getName());
    }

    /**
     * Demonstration 5: Query Methods
     */
    private void demonstrateQueryMethods() {
        System.out.println("\n🔍 DEMONSTRATION 5: Query Methods");
        System.out.println("-".repeat(60));

        // Find by name
        Optional<Author> author = authorRepository.findByName("J.K. Rowling");
        if (author.isPresent()) {
            System.out.println("✅ Find by name: " + author.get().getName());
        }

        // Find by email
        Optional<Author> authorByEmail = authorRepository.findByEmail("jk.rowling.demo@example.com");
        if (authorByEmail.isPresent()) {
            System.out.println("✅ Find by email: " + authorByEmail.get().getEmail());
        }

        // Find books by publication year
        List<Book> books1997 = bookRepository.findByPublicationYear(1997);
        System.out.println("✅ Find books by year 1997: " + books1997.size() + " books found");

        // Find books by price range
        List<Book> expensiveBooks = bookRepository.findByPriceGreaterThan(new BigDecimal("30.00"));
        System.out.println("✅ Find expensive books (>$30): " + expensiveBooks.size() + " books found");

        // Find authors with books
        List<Author> authorsWithBooks = authorRepository.findAuthorsWithBooks();
        System.out.println("✅ Find authors with books: " + authorsWithBooks.size() + " authors found");
    }

    /**
     * Demonstration 6: Lazy Loading
     */
    private void demonstrateLazyLoading() {
        System.out.println("\n⏳ DEMONSTRATION 6: Lazy Loading");
        System.out.println("-".repeat(60));

        // Get author without loading books (lazy loading)
        Author author = authorRepository.findByName("J.K. Rowling").orElse(null);
        if (author != null) {
            System.out.println("✅ Author loaded: " + author.getName());
            System.out.println("   Books collection size: " + author.getBooks().size());
            System.out.println("   (Books are loaded lazily when accessed)");
            
            // Now access books to trigger lazy loading
            author.getBooks().forEach(book -> 
                System.out.println("   - " + book.getTitle()));
        }
    }

    /**
     * Demonstration 7: Cascade Operations
     */
    private void demonstrateCascadeOperations() {
        System.out.println("\n🌊 DEMONSTRATION 7: Cascade Operations");
        System.out.println("-".repeat(60));

        // Create a new author with books (cascade will save books automatically)
        Author cascadeAuthor = new Author();
        cascadeAuthor.setName("Stephen King");
        cascadeAuthor.setEmail("stephen.king.demo@example.com");
        cascadeAuthor.setBiography("American author of horror, supernatural fiction, suspense, and fantasy novels");
        cascadeAuthor.setBirthDate(LocalDate.of(1947, 9, 21));

        Book cascadeBook = new Book();
        cascadeBook.setTitle("The Shining");
        cascadeBook.setDescription("A horror novel by Stephen King");
        cascadeBook.setPublicationYear(1977);
        cascadeBook.setIsbn("978-0385121675");
        cascadeBook.setPrice(new BigDecimal("24.99"));
        cascadeBook.setPageCount(447);

        // Add book to author (cascade will handle the relationship)
        cascadeAuthor.addBook(cascadeBook);

        // Save only the author - book will be saved automatically due to CASCADE.ALL
        Author savedAuthor = authorRepository.save(cascadeAuthor);
        System.out.println("✅ Cascade save: Author and book saved together");
        System.out.println("   Author: " + savedAuthor.getName() + " (ID: " + savedAuthor.getId() + ")");
        System.out.println("   Book: " + savedAuthor.getBooks().get(0).getTitle() + " (ID: " + savedAuthor.getBooks().get(0).getId() + ")");
    }

    /**
     * Clear existing data for clean demonstration
     */
    private void clearData() {
        System.out.println("🧹 Clearing existing data...");
        // Clear in correct order to avoid foreign key constraint violations
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        tagRepository.deleteAll();
        
        // Verify data is cleared
        long bookCount = bookRepository.count();
        long authorCount = authorRepository.count();
        long tagCount = tagRepository.count();
        
        System.out.println("✅ Data cleared successfully");
        System.out.println("   Books: " + bookCount + ", Authors: " + authorCount + ", Tags: " + tagCount);
    }
}
