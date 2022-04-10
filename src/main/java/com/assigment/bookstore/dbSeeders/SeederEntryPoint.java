package com.assigment.bookstore.dbSeeders;

import com.assigment.bookstore.book.BookRepository;
import com.assigment.bookstore.cart.CartController;
import com.assigment.bookstore.cart.CartRepository;
import com.assigment.bookstore.order.OrderRepository;
import com.assigment.bookstore.person.PersonRepository;
import com.assigment.bookstore.securityJwt.authenticationFacade.AuthenticationFacade;
import com.assigment.bookstore.securityJwt.controllers.AuthController;
import com.assigment.bookstore.securityJwt.repository.RoleRepository;
import com.assigment.bookstore.securityJwt.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class SeederEntryPoint {

    RoleRepository roleRepository;
    UserRepository userRepository;
    PersonRepository personRepository;
    BookRepository bookRepository;
    AuthenticationFacade authenticationFacade;
    AuthController authController;
    CartRepository cartRepository;
    OrderRepository orderRepository;

    RoleSeeder roleSeeder;
    UserSeeder userSeeder;
    BookSeeder bookSeeder;
    OrderSeeder orderSeeder;

    @Bean
    CommandLineRunner mainSeeder(){

        return args -> {
//            RoleSeeder roleSeeder = new RoleSeeder(roleRepository);
//            UserSeeder userSeeder = new UserSeeder(authController, roleRepository, userRepository);
//            BookSeeder bookSeeder = new BookSeeder(bookRepository, personRepository);

            roleRepository.deleteAll();
            userRepository.deleteAll();
            personRepository.deleteAll();
            bookRepository.deleteAll();
            cartRepository.deleteAll();
            orderRepository.deleteAll();

            roleSeeder.seed();
            userSeeder.seed();
            bookSeeder.seed();
            orderSeeder.seed();
        };
    }
}
