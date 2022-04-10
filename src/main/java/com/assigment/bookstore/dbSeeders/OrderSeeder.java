package com.assigment.bookstore.dbSeeders;

import com.assigment.bookstore.book.Book;
import com.assigment.bookstore.book.BookRepository;
import com.assigment.bookstore.order.OrderController;
import com.assigment.bookstore.order.OrderRepository;
import com.assigment.bookstore.order.OrderService;
import com.assigment.bookstore.order.models.Order;
import com.assigment.bookstore.order.models.OrderDto;
import com.assigment.bookstore.person.PersonRepository;
import com.assigment.bookstore.person.models.Person;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Random;

@Slf4j
@AllArgsConstructor
@Component
public class OrderSeeder implements ISeeder{


    BookRepository bookRepository;
    PersonRepository personRepository;
    OrderRepository orderRepository;
    @Override
    public void seed() {
        List<Book> books = bookRepository.findAll();
        List<Person> persons = personRepository.findAll();
        int fakedOrders = 10;
        for (int i = 0; i < fakedOrders; i++) {
            List<Book> booksId = books.subList(books.size()/(i+2), books.size());
            String randomPersonEmail = persons.get(new Random(i).nextInt(0, persons.size() - 1)).getEmail();
            Order order = new Order(
                    booksId,
                    randomPersonEmail,
                    "FakeOrder"
                    );
            orderRepository.insert(order);
        }


    }
}
