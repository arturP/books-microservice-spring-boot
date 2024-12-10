package io.artur.microservice.books;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class BookService {

    private final BookRepository repository;

    public List<BookDTO> getAll() {
        return repository.findAll().stream()
                .map(BookDTO::toBookDto)
                .toList();
    }

    public BookDTO createBook(final BookDTO bookDto) {
        return BookDTO.toBookDto(repository.save(BookDTO.toBook(bookDto)));
    }
}
