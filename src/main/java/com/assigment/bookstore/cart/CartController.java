package com.assigment.bookstore.cart;

import com.assigment.bookstore.person.Person;
import com.assigment.bookstore.person.PersonService;
import com.assigment.bookstore.securityJwt.authenticationFacade.IAuthenticationFacade;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/carts")
@AllArgsConstructor
public class CartController {


    private final PersonService personService;
    private final CartService cartService;
    private final IAuthenticationFacade authenticationFacade;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public CollectionModel<?> all(){
        return cartService.all();
    }

    @GetMapping("{personEmail}")
    public ResponseEntity<?> one(@PathVariable String personEmail){
        return cartService.one(personEmail);
    }

    @PostMapping()
    public ResponseEntity<?> addCart(@RequestBody Cart cart){
        //Cart will be added to Person of email cart.email
        return cartService.addOne(cart);
    }
    @DeleteMapping("{personEmail}")
    public ResponseEntity<?> clearCart(@PathVariable String personEmail){
        return cartService.clearOne(personEmail);
    }

}
