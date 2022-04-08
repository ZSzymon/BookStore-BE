package com.assigment.bookstore.dbSeeders;

import com.assigment.bookstore.book.Book;
import com.assigment.bookstore.book.BookRepository;
import com.assigment.bookstore.person.Person;
import com.assigment.bookstore.person.PersonRepository;
import com.assigment.bookstore.securityJwt.models.ERole;
import com.assigment.bookstore.securityJwt.models.Role;
import com.assigment.bookstore.securityJwt.models.User;
import com.assigment.bookstore.securityJwt.repository.RoleRepository;
import com.assigment.bookstore.securityJwt.repository.UserRepository;
import com.assigment.bookstore.securityJwt.security.jwt.AuthEntryPointJwt;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static com.assigment.bookstore.dbSeeders.SeederUtils.AssureRolesExistsInDb;

@Configuration
@Slf4j
public class BookSeeder {

    @Bean
    CommandLineRunner addBooks(BookRepository bookRepository, PersonRepository personRepository) {
        return args -> {
            Faker faker = new Faker();

            List<Book> books = new ArrayList<>(Arrays.asList(
                    new Book("Height Altitude Training in Iten", personRepository.findByEmail("admin@gmail.com").get(), personRepository.findByEmail("pzla@gmail.com").get()),
                    new Book("Student who is not visible", personRepository.findByEmail("admin@gmail.com").get(), personRepository.findByEmail("pzla@gmail.com").get()),
                    new Book("Suitcase - My Dear Friend", personRepository.findByEmail("umcs@gmail.com").get(), personRepository.findByEmail("pzla@gmail.com").get())
            ));

            for (int i = 0; i < 50; i++) {

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

        };


    }

    ;


}
