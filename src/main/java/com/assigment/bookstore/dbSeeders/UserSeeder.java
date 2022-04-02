package com.assigment.bookstore.dbSeeders;

import com.assigment.bookstore.person.Person;
import com.assigment.bookstore.person.PersonRepository;
import com.assigment.bookstore.securityJwt.models.ERole;
import com.assigment.bookstore.securityJwt.models.Role;
import com.assigment.bookstore.securityJwt.models.User;
import com.assigment.bookstore.securityJwt.repository.RoleRepository;
import com.assigment.bookstore.securityJwt.repository.UserRepository;
import com.assigment.bookstore.securityJwt.security.jwt.AuthEntryPointJwt;
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
public class UserSeeder {

    @Bean
    CommandLineRunner addUsers(UserRepository userRepository, PersonRepository personRepository, PasswordEncoder encoder, RoleRepository roleRepository) {
        return args -> {

            AssureRolesExistsInDb(roleRepository);

            Role userRole = roleRepository.findByName(ERole.ROLE_USER).get();
            Role mod = roleRepository.findByName(ERole.ROLE_MODERATOR).get();
            Role admin = roleRepository.findByName(ERole.ROLE_ADMIN).get();

            List<User> users = new ArrayList<>(Arrays.asList(
                    new User("user", "user@gmail.com", encoder.encode("password"), new HashSet<>(List.of(userRole))),
                    new User("mod", "mod@gmail.com", encoder.encode("password"), new HashSet<>(List.of(userRole, mod))),
                    new User("admin", "admin@gmail.com", encoder.encode("password"),
                            new HashSet<>(List.of(userRole, mod, admin))),
                    new User("PZLA", "pzla@gmail.com", encoder.encode("password"),
                            new HashSet<>(List.of(userRole))),
                    new User("UMCS", "umcs@gmail.com", encoder.encode("password"),
                            new HashSet<>(List.of(userRole))),
                    new User("WZ", "wz@gmail.com", encoder.encode("password"),
                            new HashSet<>(List.of(userRole)))

            ));

            List<Person> persons = new ArrayList<>(Arrays.asList(
                    new Person("admin@gmail.com"),
                    new Person("mod@gmail.com"),
                    new Person("user@gmail.com"),
                    new Person("pzla@gmail.com"),
                    new Person("umcs@gmail.com"),
                    new Person("wz@gmail.com")
            ));


            users.forEach(user -> userRepository.findByUsername(user.getUsername())
                    .ifPresentOrElse(u ->
                            {
                                log.info("User: " + user.getUsername() + " already exist.");
                            },
                            () -> {
                                log.info("Inserted:" + user.getUsername());
                                userRepository.save(user);
                            }));

            persons.forEach(person ->
                    personRepository.findByEmail(person.getEmail())
                            .ifPresentOrElse(role -> log.info("Person: " + person.getEmail() + " already exist."),
                                    () -> {
                                        personRepository.save(person);
                                        log.info("Inserted person: " + person.getEmail());
                                    }));

            persons.forEach(p -> {
                if (p.getUser() == null) {
                    Person person = personRepository.findByEmail(p.getEmail()).get();
                    User user = userRepository.findByEmail(p.getEmail()).get();
                    person.setUser(user);
                    personRepository.save(person);
                }


            });

            users.forEach(u -> {
                if (u.getPerson() == null) {
                    Person person = personRepository.findByEmail(u.getEmail()).get();
                    User user = userRepository.findByEmail(u.getEmail()).get();
                    user.setPerson(person);
                    userRepository.save(user);
                }
            });


        };

    }
}
