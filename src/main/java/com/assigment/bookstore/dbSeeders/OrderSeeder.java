package com.assigment.bookstore.dbSeeders;

import com.assigment.bookstore.book.Book;
import com.assigment.bookstore.book.BookRepository;
import com.assigment.bookstore.bookOrder.BookOrderRepository;
import com.assigment.bookstore.bookOrder.models.BookOrder;
import com.assigment.bookstore.person.PersonRepository;
import com.assigment.bookstore.person.models.Person;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Slf4j
@AllArgsConstructor
@Component
public class OrderSeeder implements ISeeder{


    BookRepository bookRepository;
    PersonRepository personRepository;
    BookOrderRepository bookOrderRepository;
    @Override
    public void seed() {
        List<Book> books = bookRepository.findAll();
        List<Person> persons = personRepository.findAll();
        int fakedOrders = 1;
        for (int i = 0; i < fakedOrders; i++) {
            List<Book> booksId = books.subList(0, 3);
            String randomPersonEmail = persons.get(new Random(i).nextInt(0, persons.size() - 1)).getEmail();
            BookOrder bookOrder = new BookOrder(
                    booksId,
                    randomPersonEmail,
                    "FakeOrder",
                    "");
            bookOrderRepository.insert(bookOrder);
        }


    }
}
