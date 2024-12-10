package io.artur.microservice.books;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDTO {

    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private String publishDate;

    static BookDTO toBookDto(final Book book) {
        return BookDTO.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .isbn(book.getIsbn())
                .publishDate(book.getPublishDate().toString())
                .build();
    }

    static Book toBook(final BookDTO bookDTO) {
        return Book.builder()
                .title(bookDTO.getTitle())
                .author(bookDTO.getAuthor())
                .publisher(bookDTO.getPublisher())
                .isbn(bookDTO.getIsbn())
                .publishDate(LocalDate.parse(bookDTO.getPublishDate()))
                .build();
    }
}
