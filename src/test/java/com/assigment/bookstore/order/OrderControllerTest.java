package com.assigment.bookstore.order;

import com.assigment.bookstore.book.Book;
import com.assigment.bookstore.book.BookRepository;
import com.assigment.bookstore.order.models.EOrderStatus;
import com.assigment.bookstore.order.models.Order;
import com.assigment.bookstore.order.models.OrderDto;
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
class OrderControllerTest {


    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BookRepository bookRepository;
    private URL url = new URL("http", "localhost", 8080, "/api/v1/orders/");

    OrderControllerTest() throws MalformedURLException {

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
        OrderDto orderDto = new OrderDto(orderedBooks,"Give this books :D");

        orderRepository.removeOrderByClientEmail(email);
        String payload = asJsonString(orderDto);
        mockMvc.perform(post(url.toURI())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(payload))
                .andDo(print()).andExpect(status().isCreated());

        Order order = orderRepository.findAllByClientEmail(email).get(0);
        assert order.getClientEmail().equals(email);
        assert order.getOrderList().size()==orderedBooks.size();
        assert order.getOrderStatus().equals(EOrderStatus.CREATED);
        orderRepository.removeAllByClientEmail(email);

    }

    @Test
    @WithUserDetails("user")
    void AddOneEmptyOrderBadRequest() throws Exception {
        String email = "user@gmail.com";
        //GIVEN
        OrderDto orderDto = new OrderDto(new ArrayList<>(),"Give this books :D");
        String payload = asJsonString(orderDto);
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
        OrderDto orderDto = new OrderDto(orderedBooks,"Give this books :D");

        orderRepository.removeOrderByClientEmail(email);
        String payload = asJsonString(orderDto);
        for (int i = 0; i < 2; i++) {
            mockMvc.perform(post(url.toURI())
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(payload))
                    .andDo(print()).andExpect(status().isCreated());
        }

        Order order = orderRepository.findAllByClientEmail(email).get(0);
        assert order.getClientEmail().equals(email);
        assert order.getOrderList().size()==orderedBooks.size();
        assert order.getOrderStatus().equals(EOrderStatus.CREATED);
        orderRepository.removeAllByClientEmail(email);

    }

    @Test
    void all() {

    }

    @Test
    void one() {
    }
}