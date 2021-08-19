package service;

import entity.Book;

import java.util.List;

import static io.restassured.RestAssured.get;

public class BookService {

    public static final String ENDPOINT = "/api/library/books";

    public static List<Book> getBooks() {
        return get(ENDPOINT)
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList("", Book.class);
    }
}
