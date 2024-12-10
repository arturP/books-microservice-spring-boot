package io.artur.microservice.books;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class BookControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    void testCreateBook_Success() throws Exception {
        final String title = "1984";
        final String author = "George Orwell";
        final String publisher = "Penguin Books";
        final String isbn = "9780141036144";
        final LocalDate publishDate = LocalDate.of(2008, 6, 3);

        final BookDTO bookDTO = BookDTO.builder()
                .title(title)
                .author(author)
                .publisher(publisher)
                .isbn(isbn)
                .publishDate(publishDate.toString())
                .build();

        ResultActions result = mockMvc.perform(post("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookDTO)));

        result.andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(title))
                .andExpect(jsonPath("$.author").value(author))
                .andExpect(jsonPath("$.publisher").value(publisher))
                .andExpect(jsonPath("$.isbn").value(isbn))
                .andExpect(jsonPath("$.publishDate").value(publishDate.toString()))
                .andDo(print());

        List<Book> books = bookRepository.findAll();
        assertEquals(1, books.size());
        assertEquals(title, books.getFirst().getTitle());
        assertEquals(author, books.getFirst().getAuthor());
        assertEquals(publisher, books.getFirst().getPublisher());
        assertEquals(isbn, books.getFirst().getIsbn());
        assertEquals(publishDate, books.getFirst().getPublishDate());
    }
}
