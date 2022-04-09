package com.assigment.bookstore.cart;

import com.assigment.bookstore.book.Book;
import com.assigment.bookstore.book.BookRepository;
import com.assigment.bookstore.person.PersonRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;

import static com.assigment.bookstore.util.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;



@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class CartControllerTest {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BookRepository bookRepository;
    private URL url = new URL("http", "localhost", 8080, "/api/v1/carts/");

    CartControllerTest() throws MalformedURLException {
    }

    @Before()
    public void setup() throws MalformedURLException {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    }

    @Test
    void numberOfCartsEqualPersonNumber(){
        assert personRepository.findAll().size() == cartRepository.findAll().size();
    }
    @Test
    @WithUserDetails("admin")
    void all() throws Exception {
        mockMvc.perform(get(url.toURI()))
                .andDo(print()).andExpect(status().isOk());

        assert personRepository.findAll().size() == cartRepository.findAll().size();
    }

    @Test
    @WithUserDetails("user")
    void getOneCart() throws Exception {
        String email = personRepository.findByEmail("user@gmail.com").get().getEmail();
        mockMvc.perform(get(url + email))
                .andDo(print()).andExpect(status().isOk());

        assert personRepository.findAll().size() == cartRepository.findAll().size();

    }

    @Test
    @WithUserDetails("user")
    void addCartSucces() throws Exception {
        String email = "user@gmail.com";
        Cart cart = cartRepository.findByPersonEmail(email).get();
        List<Book> books = bookRepository.findAll();
        cart.setBooks(books);

        mockMvc.perform(post(url.toURI())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(cart)))
                .andDo(print()).andExpect(status().isOk());

        assert personRepository.findAll().size() == cartRepository.findAll().size();

        Set<List<Book>> booksInDB = Set.of(personRepository.findByEmail(email).get().getCart().getBooks());
        Set<List<Book>> newBooks = Set.of(books);
        assertThat(booksInDB.containsAll(newBooks));

    }

    @Test
    @WithUserDetails("user")
    void clearCartSuccess() throws Exception {
        String email = "user@gmail.com";
        Cart cart = cartRepository.findByPersonEmail(email).get();
        List<Book> books = bookRepository.findAll();
        cart.setBooks(books);
        cartRepository.save(cart);

        mockMvc.perform(delete(url + email))
                .andDo(print()).andExpect(status().isOk());

        assert personRepository.findAll().size() == cartRepository.findAll().size();

    }

    @Test
    @WithUserDetails("user")
    void clearCartFail() throws Exception {
        String email = "admin@gmail.com";
        Cart cart = cartRepository.findByPersonEmail(email).get();
        List<Book> books = bookRepository.findAll();
        cart.setBooks(books);
        cartRepository.save(cart);

        mockMvc.perform(delete(url + email))
                .andDo(print()).andExpect(status().is(HttpStatus.FORBIDDEN.value()));

        int personsInDb = personRepository.findAll().size();
        int cartsInDB = cartRepository.findAll().size();
        assert personsInDb == cartsInDB;

    }

    @Test
    @WithUserDetails("user")
    public void addBookToCartSuccess() throws Exception {
        String bookTitle = "XWDAA W";
        String email = "user@gmail.com";

        Book book = bookRepository.findByTitle(bookTitle)
                .orElseGet(()->bookRepository.insert(new Book(bookTitle)));

        mockMvc.perform(put(url+email)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(book.getTitle()))
                .andDo(print())
                .andExpect(status().isOk());

        assert cartRepository.findByPersonEmail(email).get()
                .getBooks().stream().anyMatch(b -> b.getTitle().equals(bookTitle));
    }

}