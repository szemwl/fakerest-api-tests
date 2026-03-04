package data;

import api.model.Book;

import java.util.List;
import java.util.stream.Stream;

public class TestDataGenerator {

    public static Stream<Book> generateBooks() {

        List<String> titles = List.of("Detective", "Fantasy", "Novel");
        List<String> descriptions = List.of("Desc1", "Desc2", "Desc3");
        List<Integer> pageCounts = List.of(100, 200, 300);

        return titles.stream()
                .flatMap(title ->
                        descriptions.stream()
                                .flatMap(desc ->
                                        pageCounts.stream()
                                                .map(page ->
                                                        Book.builder()
                                                                .id((int) (Math.random() * 10000))
                                                                .title(title)
                                                                .description(desc)
                                                                .pageCount(page)
                                                                .excerpt("Excerpt")
                                                                .publishDate("2024-01-01T00:00:00")
                                                                .build()
                                                )
                                )
                );
    }

    public static Book defaultBook() {

        return Book
                .builder()
                .id(1)
                .title("Book 1")
                .description("Lorem lorem lorem. Lorem lorem lorem. Lorem lorem lorem.\n")
                .pageCount(100)
                .excerpt("Lorem lorem lorem. Lorem lorem lorem. Lorem lorem lorem.\n" +
                        "Lorem lorem lorem. Lorem lorem lorem. Lorem lorem lorem.\n" +
                        "Lorem lorem lorem. Lorem lorem lorem. Lorem lorem lorem.\n" +
                        "Lorem lorem lorem. Lorem lorem lorem. Lorem lorem lorem.\n" +
                        "Lorem lorem lorem. Lorem lorem lorem. Lorem lorem lorem.\n")
                .publishDate("2026-03-03T10:38:24.3862865+00:00")
                .build();
    }
}
