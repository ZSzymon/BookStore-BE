package com.assigment.bookstore.dbSeeders;

import com.assigment.bookstore.cart.Cart;
import com.assigment.bookstore.cart.CartRepository;
import com.assigment.bookstore.exceptions.NotFoundException;
import com.assigment.bookstore.person.Person;
import com.assigment.bookstore.person.PersonRepository;
import com.assigment.bookstore.securityJwt.controllers.AuthController;
import com.assigment.bookstore.securityJwt.models.ERole;
import com.assigment.bookstore.securityJwt.models.Role;
import com.assigment.bookstore.securityJwt.models.User;
import com.assigment.bookstore.securityJwt.payload.request.SignupRequest;
import com.assigment.bookstore.securityJwt.repository.RoleRepository;
import com.assigment.bookstore.securityJwt.repository.UserRepository;
import com.github.javafaker.Faker;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.assigment.bookstore.dbSeeders.SeederUtils.AssureRolesExistsInDb;

@AllArgsConstructor
@Component
public class UserSeeder implements ISeeder {

    AuthController authController;
    RoleRepository roleRepository;
    UserRepository userRepository;

//    @Bean("addUsers")
//    CommandLineRunner addUsers(UserRepository userRepository, PersonRepository personRepository, PasswordEncoder encoder, RoleRepository roleRepository) {
//        return args -> {
//
//            AssureRolesExistsInDb(roleRepository);
//
//            Role userRole = roleRepository.findByName(ERole.ROLE_USER).get();
//            Role mod = roleRepository.findByName(ERole.ROLE_MODERATOR).get();
//            Role admin = roleRepository.findByName(ERole.ROLE_ADMIN).get();
//
//            List<User> users = new ArrayList<>(Arrays.asList(
//                    new User("user", "user@gmail.com", encoder.encode("password"), new HashSet<>(List.of(userRole))),
//                    new User("mod", "mod@gmail.com", encoder.encode("password"), new HashSet<>(List.of(userRole, mod))),
//                    new User("admin", "admin@gmail.com", encoder.encode("password"),
//                            new HashSet<>(List.of(userRole, mod, admin))),
//                    new User("PZLA", "pzla@gmail.com", encoder.encode("password"),
//                            new HashSet<>(List.of(userRole))),
//                    new User("UMCS", "umcs@gmail.com", encoder.encode("password"),
//                            new HashSet<>(List.of(userRole))),
//                    new User("WZ", "wz@gmail.com", encoder.encode("password"),
//                            new HashSet<>(List.of(userRole)))
//
//            ));
//            Faker faker = new Faker(new Locale("pl_PL"));
//            for (int i = 0; i < 1; i++) {
//                String userName = faker.name().username().toLowerCase(Locale.ROOT);
//                User u = new User(userName, userName+"@gmail.com",String.valueOf("password".hashCode()));
//                users.add(u);
//            }
//
//            List<Person> persons = new ArrayList<>();
//            users.forEach(u -> {
//                persons.add(new Person(u.getEmail()));
//            });
//
//
//            users.forEach(user -> userRepository.findByUsername(user.getUsername())
//                    .ifPresentOrElse(u ->
//                            {
//                                log.info("User: " + user.getUsername() + " already exist.");
//                            },
//                            () -> {
//                                log.info("Inserted:" + user.getUsername());
//                                userRepository.save(user);
//                            }));
//
//            persons.forEach(person ->
//                    personRepository.findByEmail(person.getEmail())
//                            .ifPresentOrElse(role -> log.info("Person: " + person.getEmail() + " already exist."),
//                                    () -> {
//                                        personRepository.save(person);
//                                        log.info("Inserted person: " + person.getEmail());
//                                    }));
//
//            persons.forEach(p -> {
//                if (p.getUser() == null) {
//                    Person person = personRepository.findByEmail(p.getEmail()).get();
//                    User user = userRepository.findByEmail(p.getEmail()).get();
//                    person.setUser(user);
//                    personRepository.save(person);
//                }
//
//
//            });
//
//            users.forEach(u -> {
//                if (u.getPerson() == null) {
//                    Person person = personRepository.findByEmail(u.getEmail()).get();
//                    User user = userRepository.findByEmail(u.getEmail()).get();
//                    user.setPerson(person);
//                    userRepository.save(user);
//                }
//            });
//
//
//        };
//
//    }

    void assertEveryBodyHasCart(PersonRepository personRepository, CartRepository cartRepository){
        List<Person> people = personRepository.findAll();
        people.forEach(person -> {
            if (person.getCart() == null) {
                Cart c = cartRepository.findByPersonEmail(person.getEmail())
                        .orElseGet(()->cartRepository.save(new Cart(person.getEmail())));
                person.setCart(c);
                personRepository.save(person);
            }
        });

    }
    public void seed(){
        AssureRolesExistsInDb(roleRepository);

        Role userRole = roleRepository.findByName(ERole.ROLE_USER).get();
        Role mod = roleRepository.findByName(ERole.ROLE_MODERATOR).get();
        Role admin = roleRepository.findByName(ERole.ROLE_ADMIN).get();

        assertAdminAndModCreation(authController, userRepository, userRole, mod, admin);
        Faker fake = new Faker();
        for (int i = 0; i < 10; i++) {
            String login = fake.name().username();
            SignupRequest request = new SignupRequest(login, login+"@gmail.com", "password");
            authController.registerUser(request);
        }

    }

    private void assertAdminAndModCreation(AuthController authController, UserRepository userRepository, Role userRole, Role mod, Role admin) {
        if (userRepository.findByUsername("admin").isEmpty()) {
            authController.registerUser(new SignupRequest("admin", "admin@gmail.com", "password"));
            User adminUser = userRepository.findByEmail("admin@gmail.com").orElseThrow(() -> new NotFoundException("User", "admin@gmail.com"));
            adminUser.setRoles(Set.of(admin, mod, userRole));
            userRepository.save(adminUser);
        }
        if (userRepository.findByUsername("mod").isEmpty()) {
            authController.registerUser(new SignupRequest("mod", "mod@gmail.com", "password"));
            User modUser = userRepository.findByEmail("mod@gmail.com").orElseThrow(() -> new NotFoundException("User", "mod@gmail.com"));
            modUser.setRoles(Set.of(mod, userRole));
            userRepository.save(modUser);
        }

    }

}
