package com.acu.graphql;

public class CreateReviewInput {
    private String bookId;
    private Integer rating;
    private String comment;
    
    public CreateReviewInput() {}
    
    public CreateReviewInput(String bookId, Integer rating, String comment) {
        this.bookId = bookId;
        this.rating = rating;
        this.comment = comment;
    }
    
    public String getBookId() {
        return bookId;
    }
    
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
    
    public Integer getRating() {
        return rating;
    }
    
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
}
