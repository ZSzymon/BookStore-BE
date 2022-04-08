package com.assigment.bookstore.book;

import com.assigment.bookstore.person.Person;
import com.assigment.bookstore.person.PersonDTO;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.MalformedURLException;
import java.net.URL;

import static com.assigment.bookstore.util.asJsonString;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class BookControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    BookControllerTest() throws MalformedURLException {
    }


    @Before()
    public void setup()
    {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    private URL url = new URL("http", "localhost", 8080, "/api/v1");

    @Autowired
    BookController bookController;

    @Autowired
    BookRepository bookRepository;
    @Test
    void all() throws Exception {
        mockMvc.perform(get(url + "/books"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void getOne() throws Exception {
        //GIVEN
        bookRepository.findByTitle("Height Altitude Training in Iten").ifPresentOrElse(book -> {},
                ()-> bookRepository.insert(new Book("Height Altitude Training in Iten")));
        //WHEN
        mockMvc.perform(get(url + "/books/"+"Height Altitude Training in Iten"))
                .andDo(print())
                //THEN
                .andExpect(status().isOk());

        //CLEAN
        bookRepository.findByTitle("Height Altitude Training in Iten").ifPresentOrElse(book -> {},
                ()-> bookRepository.removeBookByTitle("Height Altitude Training in Iten"));
    }

    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN", "MOD", "USER"})
    void addOneAsAdminWhenAuthorOrPublisherNotExistInDB() throws Exception {
        //GIVEN
        PersonDTO author = new PersonDTO(new Person("ghostwriter@gmail.com"));
        PersonDTO publisher = new PersonDTO(new Person("umcs@gmail.com"));
        BookDTO bookDTO = new BookDTO("The book that shouldn't exist", author, publisher);

        bookRepository.findByTitle(bookDTO.getTitle()).ifPresent(
                book -> {bookRepository.delete(book);}
        );
        //WHEN
        mockMvc.perform(post(url+"/books")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(bookDTO)))
                //THEN
                .andExpect(status().is(HttpStatus.CREATED.value()));
        assert bookRepository.findByTitle("The book that shouldn't exist").isPresent();
    }

    @WithMockUser(value = "user", roles = {"USER"})
    @Test
    void addOneAsUserFail() throws Exception {
        //GIVEN
        PersonDTO author = new PersonDTO(new Person("ghostwriter@gmail.com"));
        PersonDTO publisher = new PersonDTO(new Person("umcs@gmail.com"));
        BookDTO bookDTO = new BookDTO("The book that shouldn't exist", author, publisher);

        bookRepository.findByTitle(bookDTO.getTitle()).ifPresent(
                book -> {bookRepository.delete(book);}
        );
        //WHEN
        mockMvc.perform(post(url+"/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bookDTO)))
                //THEN
                .andExpect(status().is(HttpStatus.FORBIDDEN.value()));
        assert bookRepository.findByTitle("The book that shouldn't exist").isEmpty();

    }

    @Test
    void removeOne() {
    }
}