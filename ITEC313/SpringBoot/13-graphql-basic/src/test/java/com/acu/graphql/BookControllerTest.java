package com.acu.graphql;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.graphql.test.tester.GraphQlTester;

@GraphQlTest(BookController.class)
class BookControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    void shouldReturnBookWithAuthor() {
        String document = """
                query {
                    bookById(id: "book-1") {
                        id
                        name
                        pageCount
                        author {
                            id
                            firstName
                            lastName
                        }
                    }
                }
                """;

        graphQlTester.document(document)
                .execute()
                .path("data.bookById.id")
                .entity(String.class)
                .isEqualTo("book-1");

        graphQlTester.document(document)
                .execute()
                .path("data.bookById.name")
                .entity(String.class)
                .isEqualTo("The Lucky Country");

        graphQlTester.document(document)
                .execute()
                .path("data.bookById.author.firstName")
                .entity(String.class)
                .isEqualTo("Donald");

        graphQlTester.document(document)
                .execute()
                .path("data.bookById.author.lastName")
                .entity(String.class)
                .isEqualTo("Horne");
    }

    @Test
    void shouldReturnSecondBookWithAuthor() {
        String document = """
                query {
                    bookById(id: "book-2") {
                        id
                        name
                        pageCount
                        author {
                            id
                            firstName
                            lastName
                        }
                    }
                }
                """;

        graphQlTester.document(document)
                .execute()
                .path("data.bookById.id")
                .entity(String.class)
                .isEqualTo("book-2");

        graphQlTester.document(document)
                .execute()
                .path("data.bookById.name")
                .entity(String.class)
                .isEqualTo("The Magic Pudding: Being The Adventures of Bunyip Bluegum and his friends Bill Barnacle and Sam Sawnoff");

        graphQlTester.document(document)
                .execute()
                .path("data.bookById.author.firstName")
                .entity(String.class)
                .isEqualTo("Norman");

        graphQlTester.document(document)
                .execute()
                .path("data.bookById.author.lastName")
                .entity(String.class)
                .isEqualTo("Lindsay");
    }

    @Test
    void shouldReturnNullForNonExistentBook() {
        String document = """
                query {
                    bookById(id: "non-existent") {
                        id
                        name
                        pageCount
                        author {
                            id
                            firstName
                            lastName
                        }
                    }
                }
                """;

        graphQlTester.document(document)
                .execute()
                .path("data.bookById")
                .valueIsNull();
    }
}
