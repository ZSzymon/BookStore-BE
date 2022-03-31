package com.assigment.bookstore.dbSeeders;


import com.assigment.bookstore.person.Person;
import com.assigment.bookstore.securityJwt.security.jwt.AuthEntryPointJwt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class PersonSeeder {

    private static final Logger log = LoggerFactory.getLogger(AuthEntryPointJwt.class);
    private static final List<Person> persons = new ArrayList<>(Arrays.asList(
            new Person("szymonzywko@gmail.com"),
            Person.builder().email("jacek@gmail.com").firstName("Jacek").secondName("Jack").build()
    ));


//    @Bean
//    CommandLineRunner addPersons(PersonRepository repository){
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
}
