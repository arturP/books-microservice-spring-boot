package io.artur.microservice.books;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
class BookController {

    private final BookService service;

    @GetMapping("/all")
    ResponseEntity<List<BookDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    ResponseEntity<BookDTO> createBook(@Valid @RequestBody final BookDTO bookDto) {
        return new ResponseEntity<>(service.createBook(bookDto), HttpStatus.CREATED);
    }
}
