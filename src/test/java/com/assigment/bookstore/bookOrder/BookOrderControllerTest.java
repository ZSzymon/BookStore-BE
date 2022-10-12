package com.assigment.bookstore.bookOrder;

import com.assigment.bookstore.book.Book;
import com.assigment.bookstore.book.BookRepository;
import com.assigment.bookstore.bookOrder.models.EBookOrderStatus;
import com.assigment.bookstore.bookOrder.models.BookOrder;
import com.assigment.bookstore.bookOrder.models.BookOrderDto;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.assigment.bookstore.util.asJsonString;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
class BookOrderControllerTest {


    @Autowired
    private BookOrderRepository bookOrderRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BookRepository bookRepository;
    private URL url = new URL("http", "localhost", 8080, "/api/v1/bookorders/");

    BookOrderControllerTest() throws MalformedURLException {

    }


    @Before()
    public void setup() throws MalformedURLException {
        //Init MockMvc Object and build
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    }

    @Test
    @WithUserDetails("user")
    void addOne() throws Exception {
        String email = "user@gmail.com";
        //GIVEN
        List<Book> allBooks = bookRepository.findAll();
        int allBooksSize = allBooks.size();
        List<String> orderedBooks = allBooks.subList(0, allBooksSize / 10).stream().map(Book::getId).toList();
        BookOrderDto bookOrderDto = new BookOrderDto(orderedBooks,"Give this books :D");

        bookOrderRepository.removeOrderByClientEmail(email);
        String payload = asJsonString(bookOrderDto);
        mockMvc.perform(post(url.toURI())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(payload))
                .andDo(print()).andExpect(status().isCreated());

        BookOrder bookOrder = bookOrderRepository.findAllByClientEmail(email).get(0);
        Double totalOrderAmount = bookOrder.getTotalOrderAmount();
        assert totalOrderAmount.intValue() > 0;
        assert bookOrder.getClientEmail().equals(email);
        assert bookOrder.getOrderList().size()==orderedBooks.size();
        assert bookOrder.getOrderStatus().equals(EBookOrderStatus.CREATED);
        bookOrderRepository.removeAllByClientEmail(email);

    }

    @Test
    @WithUserDetails("user")
    void AddOneEmptyOrderBadRequest() throws Exception {
        String email = "user@gmail.com";
        //GIVEN
        BookOrderDto bookOrderDto = new BookOrderDto(new ArrayList<>(),"Give this books :D");
        String payload = asJsonString(bookOrderDto);
        //WHEN
        mockMvc.perform(post(url.toURI())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(payload))
                //THEN
                .andDo(print()).andExpect(status().isBadRequest());


    }


    @Test
    @WithUserDetails("user")
    void addTwo() throws Exception {
        String email = "user@gmail.com";
        //GIVEN
        List<Book> allBooks = bookRepository.findAll();
        int allBooksSize = allBooks.size();
        List<String> orderedBooks = allBooks.subList(0, allBooksSize / 10).stream().map(Book::getId).toList();
        BookOrderDto bookOrderDto = new BookOrderDto(orderedBooks,"Give this books :D");

        bookOrderRepository.removeOrderByClientEmail(email);
        String payload = asJsonString(bookOrderDto);
        for (int i = 0; i < 2; i++) {
            mockMvc.perform(post(url.toURI())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(payload))
                    .andDo(print()).andExpect(status().isCreated());
        }

        BookOrder bookOrder = bookOrderRepository.findAllByClientEmail(email).get(0);
        assert bookOrder.getClientEmail().equals(email);
        assert bookOrder.getOrderList().size()==orderedBooks.size();
        assert bookOrder.getOrderStatus().equals(EBookOrderStatus.CREATED);
        bookOrderRepository.removeAllByClientEmail(email);

    }

    @Test
    void all() {

    }

    @Test
    void one() {
    }
}