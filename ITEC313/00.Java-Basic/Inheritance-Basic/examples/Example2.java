/**
 * Example 2: Advanced Inheritance - Media Library Hierarchy
 * 
 * This example demonstrates:
 * - Abstract classes and methods
 * - Interface implementation
 * - Multiple inheritance through interfaces
 * - Polymorphism with collections
 * 
 * Key Differences from Python:
 * - Java has explicit abstract classes and interfaces
 * - Java interfaces provide multiple inheritance
 * - Java uses 'implements' for interfaces
 */

// Abstract base class for all media items
abstract class MediaItem {
    protected String title;
    protected String creator;
    protected int year;
    protected double rating;
    
    public MediaItem(String title, String creator, int year) {
        this.title = title;
        this.creator = creator;
        this.year = year;
        this.rating = 0.0;
    }
    
    // Abstract method - must be implemented by subclasses
    public abstract void play();
    
    // Concrete method - inherited by all subclasses
    public void setRating(double rating) {
        if (rating >= 0.0 && rating <= 5.0) {
            this.rating = rating;
            System.out.println("  Rating set to: " + rating);
        }
    }
    
    public void displayInfo() {
        System.out.println("  Title: " + title);
        System.out.println("  Creator: " + creator);
        System.out.println("  Year: " + year);
        System.out.println("  Rating: " + rating);
    }
}

// Interface for items that can be borrowed
interface Borrowable {
    void borrow(String borrower);
    void returnItem();
    boolean isAvailable();
}

// Interface for items that can be rated
interface Rateable {
    void rate(double rating);
    double getAverageRating();
}

// Book class extends MediaItem and implements Borrowable
class Book extends MediaItem implements Borrowable, Rateable {
    private String isbn;
    private int pages;
    private String borrower;
    private boolean available;
    private int ratingCount;
    private double totalRating;
    
    public Book(String title, String author, int year, String isbn, int pages) {
        super(title, author, year);
        this.isbn = isbn;
        this.pages = pages;
        this.available = true;
        this.ratingCount = 0;
        this.totalRating = 0.0;
    }
    
    @Override
    public void play() {
        System.out.println("  Reading book: " + title);
    }
    
    @Override
    public void borrow(String borrower) {
        if (available) {
            this.borrower = borrower;
            this.available = false;
            System.out.println("  Book borrowed by: " + borrower);
        } else {
            System.out.println("  Book is not available");
        }
    }
    
    @Override
    public void returnItem() {
        if (!available) {
            this.borrower = null;
            this.available = true;
            System.out.println("  Book returned");
        }
    }
    
    @Override
    public boolean isAvailable() {
        return available;
    }
    
    @Override
    public void rate(double rating) {
        if (rating >= 0.0 && rating <= 5.0) {
            totalRating += rating;
            ratingCount++;
            this.rating = totalRating / ratingCount;
            System.out.println("  Book rated: " + rating);
        }
    }
    
    @Override
    public double getAverageRating() {
        return rating;
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("  ISBN: " + isbn);
        System.out.println("  Pages: " + pages);
        System.out.println("  Available: " + available);
        if (!available) {
            System.out.println("  Borrowed by: " + borrower);
        }
    }
}

// Movie class extends MediaItem and implements Rateable
class Movie extends MediaItem implements Rateable {
    private String director;
    private int duration; // in minutes
    private String genre;
    private int ratingCount;
    private double totalRating;
    
    public Movie(String title, String director, int year, int duration, String genre) {
        super(title, director, year);
        this.director = director;
        this.duration = duration;
        this.genre = genre;
        this.ratingCount = 0;
        this.totalRating = 0.0;
    }
    
    @Override
    public void play() {
        System.out.println("  Playing movie: " + title);
        System.out.println("  Duration: " + duration + " minutes");
    }
    
    @Override
    public void rate(double rating) {
        if (rating >= 0.0 && rating <= 5.0) {
            totalRating += rating;
            ratingCount++;
            this.rating = totalRating / ratingCount;
            System.out.println("  Movie rated: " + rating);
        }
    }
    
    @Override
    public double getAverageRating() {
        return rating;
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("  Director: " + director);
        System.out.println("  Duration: " + duration + " minutes");
        System.out.println("  Genre: " + genre);
    }
}

// Music class extends MediaItem
class Music extends MediaItem {
    private String artist;
    private String album;
    private int trackNumber;
    
    public Music(String title, String artist, int year, String album, int trackNumber) {
        super(title, artist, year);
        this.artist = artist;
        this.album = album;
        this.trackNumber = trackNumber;
    }
    
    @Override
    public void play() {
        System.out.println("  Playing music: " + title);
        System.out.println("  Artist: " + artist);
        System.out.println("  Album: " + album);
    }
    
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("  Artist: " + artist);
        System.out.println("  Album: " + album);
        System.out.println("  Track: " + trackNumber);
    }
}

// Main class to demonstrate advanced inheritance
public class Example2 {
    public static void main(String[] args) {
        System.out.println("=== Advanced Inheritance Example ===\n");
        
        // Create different types of media items
        Book book = new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, "978-0743273565", 180);
        Movie movie = new Movie("The Matrix", "Lana Wachowski", 1999, 136, "Sci-Fi");
        Music music = new Music("Bohemian Rhapsody", "Queen", 1975, "A Night at the Opera", 1);
        
        // Demonstrate basic functionality
        System.out.println("Book Information:");
        book.displayInfo();
        book.play();
        
        System.out.println("\nMovie Information:");
        movie.displayInfo();
        movie.play();
        
        System.out.println("\nMusic Information:");
        music.displayInfo();
        music.play();
        
        // Demonstrate interface functionality
        System.out.println("\n=== Interface Demonstrations ===");
        
        // Borrowable interface
        System.out.println("Book Borrowing:");
        book.borrow("Alice Johnson");
        book.displayInfo();
        book.returnItem();
        book.displayInfo();
        
        // Rateable interface
        System.out.println("\nRating System:");
        book.rate(4.5);
        book.rate(4.0);
        movie.rate(5.0);
        movie.rate(4.5);
        
        System.out.println("Book average rating: " + book.getAverageRating());
        System.out.println("Movie average rating: " + movie.getAverageRating());
        
        // Demonstrate polymorphism
        System.out.println("\n=== Polymorphism Demo ===");
        MediaItem[] library = {book, movie, music};
        
        for (MediaItem item : library) {
            System.out.println("Playing media item:");
            item.play();
            item.setRating(4.0);
            System.out.println();
        }
        
        System.out.println("=== Example Complete ===");
    }
} 