package com.assigment.bookstore.dbSeeders;

import com.assigment.bookstore.securityJwt.models.ERole;
import com.assigment.bookstore.securityJwt.models.Role;
import com.assigment.bookstore.securityJwt.repository.RoleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
@AllArgsConstructor
public class RoleSeeder implements ISeeder {


    RoleRepository roleRepository;


    public void seed() {
        Arrays.stream(ERole.values()).forEach(eRole ->
                roleRepository.findByName(eRole)
                        .ifPresentOrElse(role -> {log.info("Role: " + eRole.toString() + " already exist.");
                            },
                                () -> {
                            roleRepository.save(new Role(eRole));
                            log.info("Inserted role: " + eRole.toString());
                        }));

        }

}
