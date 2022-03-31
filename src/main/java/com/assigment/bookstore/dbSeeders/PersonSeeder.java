//package com.assigment.bookstore.dbSeeders;
//
//
//import com.assigment.bookstore.person.Person;
//import com.assigment.bookstore.person.PersonRepository;
//import com.assigment.bookstore.securityJwt.models.User;
//import com.assigment.bookstore.securityJwt.repository.UserRepository;
//import com.assigment.bookstore.securityJwt.security.jwt.AuthEntryPointJwt;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//@Configuration
//public class PersonSeeder {
//
//    private static final Logger log = LoggerFactory.getLogger(AuthEntryPointJwt.class);
//    private static final List<Person> persons = new ArrayList<>(Arrays.asList(
//            new Person("admin@gmail.com"),
//            new Person("mod@gmail.com"),
//            new Person("user@gmail.com")
//    ));
//
//
//    @Bean
//    CommandLineRunner addPersons(PersonRepository repository) {
//        return args -> {
//            persons.forEach(person ->
//                    repository.findByEmail(person.getEmail())
//                            .ifPresentOrElse(role -> log.info("Person: " + person.getEmail() + " already exist."),
//                                    () -> {
//                                        repository.save(person);
//                                        log.info("Inserted person: " + person.getEmail());
//                                    }));
//        };
//    }
//
//
//}
