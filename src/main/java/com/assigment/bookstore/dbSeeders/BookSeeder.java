package com.assigment.bookstore.dbSeeders;

import com.assigment.bookstore.book.Book;
import com.assigment.bookstore.book.BookRepository;
import com.assigment.bookstore.person.PersonRepository;
import com.assigment.bookstore.securityJwt.controllers.AuthController;
import com.assigment.bookstore.securityJwt.payload.request.SignupRequest;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class BookSeeder implements ISeeder {


    BookRepository bookRepository;
    PersonRepository personRepository;
    AuthController authController;


    private com.assigment.bookstore.person.models.Person findOrCreate(PersonRepository repository, String email){
        com.assigment.bookstore.person.models.Person p = repository.findByEmail(email).orElseGet(()->{
            String login = email.substring(0, email.indexOf("@"));
            SignupRequest signupRequest = new SignupRequest(login, email, "password");
            authController.registerUser(signupRequest);
            return repository.findByEmail(email).get();
        });
        return p;
    }
    public void seed() {

        Faker faker = new Faker();
        List<com.assigment.bookstore.person.models.Person> persons = personRepository.findAll();
        List<Book> books = new ArrayList<>(Arrays.asList(
                new Book("Height Altitude Training in Iten", findOrCreate(personRepository,"admin@gmail.com"),
                        findOrCreate(personRepository,"mod@gmail.com")),
                new Book("Student who is not visible", findOrCreate(personRepository, "admin@gmail.com"),
                        findOrCreate(personRepository,"mod@gmail.com")),
                new Book("Suitcase - My Dear Friend", findOrCreate(personRepository,"umcs@gmail.com"),
                        findOrCreate(personRepository,"mod@gmail.com"))
        ));

        for (int i = 0; i < 30; i++) {
            int personSize = persons.size();
            if(personSize==0){
                break;
            }
            Book b = new Book(faker.book().title(),
                    persons.get(faker.random().nextInt(0, personSize-1)),
                    persons.get(faker.random().nextInt(0, personSize-1)));
            books.add(b);
        }
        books.forEach(book -> {
            bookRepository.findByTitle(book.getTitle())
                    .ifPresentOrElse(b -> {
                        log.info("Book:" + b.getTitle() + " already exist.");
                        },
                            () -> {
                        bookRepository.insert(book);
                        log.info("Book: "+ book.getTitle()+" inserted.");
                    });
        });




    }




}
