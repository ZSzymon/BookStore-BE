package com.assigment.bookstore.person;

import com.assigment.bookstore.person.models.Person;
import com.assigment.bookstore.securityJwt.authenticationFacade.IAuthenticationFacade;
import com.assigment.bookstore.securityJwt.security.services.UserDetailsImpl;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/persons")
@AllArgsConstructor
public class PersonController {

    private final PersonService personService;
    private final IAuthenticationFacade authenticationFacade;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public CollectionModel<EntityModel<Person>> all(){
        return personService.all();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("{email}")
    public ResponseEntity<EntityModel<Person>> one(@PathVariable String email){
        return personService.getByEmail(email);
    }

    @GetMapping("/me")
    public ResponseEntity<EntityModel<Person>> me(){
        UserDetailsImpl userDetails = authenticationFacade.getUserDetailsImpl();
        return personService.getByEmail(userDetails.getEmail());
    }
    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<EntityModel<Person>> addOne(@RequestBody Person person){
        return personService.addOne(person);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{email}")
    public ResponseEntity<?> removeOne(@PathVariable String email){
        return personService.removeOne(email);
    }

}
