package com.assigment.bookstore.dbSeeders;

import com.assigment.bookstore.securityJwt.models.ERole;
import com.assigment.bookstore.securityJwt.models.Role;
import com.assigment.bookstore.securityJwt.models.User;
import com.assigment.bookstore.securityJwt.repository.RoleRepository;
import com.assigment.bookstore.securityJwt.repository.UserRepository;
import com.assigment.bookstore.securityJwt.security.jwt.AuthEntryPointJwt;
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

@Configuration
public class UserSeeder {
    private static final Logger log = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    @Bean
    CommandLineRunner addUsers(UserRepository repository, PasswordEncoder encoder, RoleRepository roleRepository) {
        return args -> {

            if (roleRepository.findByName(ERole.ROLE_USER).isEmpty()){
                roleRepository.insert(new Role(ERole.ROLE_USER));
            }
            if (roleRepository.findByName(ERole.ROLE_ADMIN).isEmpty()){
                roleRepository.insert(new Role(ERole.ROLE_ADMIN));
            }if (roleRepository.findByName(ERole.ROLE_MODERATOR).isEmpty()){
                roleRepository.insert(new Role(ERole.ROLE_MODERATOR));
            }

            Role userRole = roleRepository.findByName(ERole.ROLE_USER).get();
            Role mod = roleRepository.findByName(ERole.ROLE_MODERATOR).get();
            Role admin = roleRepository.findByName(ERole.ROLE_ADMIN).get();

            List<User> users = new ArrayList<>(Arrays.asList(
                    new User("user", "user@gmail.com", encoder.encode("password"), new HashSet<>(List.of(userRole))),
                    new User("mod", "mod@gmail.com", encoder.encode("password"),  new HashSet<>(List.of(userRole, mod))),
                    new User("admin", "admin@gmail.com", encoder.encode("password"),
                            new HashSet<>(List.of(userRole, mod, admin)))

            ));
            users.forEach(user -> repository.findByUsername(user.getUsername())
                    .ifPresentOrElse(u -> log.info("User: " + user.getUsername() + " already exist."),
                            () -> {
                                log.info("Inserted:" + user.getUsername());
                                repository.save(user);
                            }));
        };

    }
}
