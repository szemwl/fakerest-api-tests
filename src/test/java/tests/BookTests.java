package tests;

import api.model.Book;
import api.spec.RequestSpec;
import api.spec.ResponseSpec;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class BookTests {

    @Test
    void testCreateBook() {

        Book book = Book.builder()
                .id(9999)
                .title("Test Book")
                .description("Test Description")
                .pageCount(100)
                .excerpt("Excerpt")
                .publishDate("2024-01-01T00:00:00")
                .build();

        Book response =
                given()
                        .spec(RequestSpec.requestSpec())
                        .body(book)
                        .when()
                        .post("/api/v1/Books")
                        .then()
                        .spec(ResponseSpec.ok200())
                        .extract()
                        .as(Book.class);

        assertNotNull(response.getId());
        assertEquals("Test Book", response.getTitle());
    }

    @Test
    void testGetBookById() {
        int bookId = 1;

        Book received =
                given()
                        .spec(RequestSpec.requestSpec())
                        .when()
                        .get("/api/v1/Books/" + bookId)
                        .then()
                        .spec(ResponseSpec.ok200())
                        .extract()
                        .as(Book.class);

        assertEquals(bookId, received.getId());
        assertNotNull(received.getTitle());
    }

    @Test
    void testGetAllBooks() {

        Book[] books =
                given()
                        .spec(RequestSpec.requestSpec())
                        .when()
                        .get("/api/v1/Books")
                        .then()
                        .spec(ResponseSpec.ok200())
                        .extract()
                        .as(Book[].class);

        assertNotNull(books);
        assertTrue(books.length > 0);
    }

    @Test
    void testDeleteBook() {
        int bookId = 1;

        given()
                .spec(RequestSpec.requestSpec())
                .when()
                .delete("/api/v1/Books/" + bookId)
                .then()
                .spec(ResponseSpec.ok200());
    }

    @Test
    void testNegativeGetNonExistingBook() {
        int nonExistingId = 999_999_999;

        given()
                .spec(RequestSpec.requestSpec())
                .when()
                .get("/api/v1/Books/" + nonExistingId)
                .then()
                .spec(ResponseSpec.notFound404());
    }

    @ParameterizedTest
    @MethodSource("utils.TestDataGenerator#generateBooks")
    void testCreateBookParameterized(Book book) {

        given()
                .spec(RequestSpec.requestSpec())
                .body(book)
                .when()
                .post("/api/v1/Books")
                .then()
                .spec(ResponseSpec.ok200());
    }

    @Test
    void testBookSchema() {

        Book book = Book.builder()
                .id(5555)
                .title("Schema Test")
                .description("Desc")
                .pageCount(123)
                .excerpt("Ex")
                .publishDate("2024-01-01T00:00:00")
                .build();

        given()
                .spec(RequestSpec.requestSpec())
                .body(book)
                .when()
                .post("/api/v1/Books")
                .then()
                .spec(ResponseSpec.ok200())
                .body(matchesJsonSchemaInClasspath("book-schema.json"));
    }
}