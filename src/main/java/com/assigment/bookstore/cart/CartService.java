package com.assigment.bookstore.cart;

import com.assigment.bookstore.book.Book;
import com.assigment.bookstore.book.BookRepository;
import com.assigment.bookstore.exceptions.NotFoundException;
import com.assigment.bookstore.person.CartAssembler;
import com.assigment.bookstore.person.PersonRepository;
import com.assigment.bookstore.securityJwt.authenticationFacade.AuthenticationFacade;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CartService {

    AuthenticationFacade authenticationFacade;
    CartAssembler cartAssembler;
    CartRepository cartRepository;
    PersonRepository personRepository;
    BookRepository bookRepository;

    public ResponseEntity<?> one(String personEmail) {
        if (!authenticationFacade.isModifingOwnData(personEmail) && !authenticationFacade.isAdmin()) {
            return new ResponseEntity<>("You are not allowed to see " + personEmail + "cart.", HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(cartRepository.findByPersonEmail(personEmail)
                .map(cartAssembler::toModel)
                .orElseThrow(()-> new NotFoundException("Cart", personEmail)));
    }

    public CollectionModel<?> all() {
        return cartAssembler.toCollectionModel(cartRepository.findAll());
    }

    public ResponseEntity addOne(Cart newCart) {
        cartRepository.findByPersonEmail(newCart.getPersonEmail()).ifPresentOrElse(
                c ->{
                    c.setBooks(newCart.getBooks());
                    cartRepository.save(c);

                },
                ()->{
                    cartRepository.save(newCart);
                }
        );
        Optional<com.assigment.bookstore.person.models.Person> personOptional = personRepository.findByEmail(newCart.getPersonEmail());
        if (personOptional.isPresent()) {
            personOptional.get().setCart(newCart);
            personRepository.save(personOptional.get());
        }
        return ResponseEntity.ok("");
    }

    public ResponseEntity<?> clearOne(String personEmail) {
        if(!isAdminOrDataOwner(personEmail)){
            return new ResponseEntity<>("You are not allowed to clear " + personEmail + "cart.", HttpStatus.FORBIDDEN);
        }
        Optional<com.assigment.bookstore.person.models.Person> personOptional = personRepository.findByEmail(personEmail);
        if (personOptional.isPresent()) {
            com.assigment.bookstore.person.models.Person p = personOptional.get();
            Cart c = p.getCart();
            c.setBooks(new ArrayList<>());
            p.setCart(c);
        }
        return ResponseEntity.ok("");

    }

    public ResponseEntity<?> addBookToCart(String personEmail, String title) {
        if(!isAdminOrDataOwner(personEmail)){
            return new ResponseEntity<>("You are not allowed to clear " + personEmail + "cart.", HttpStatus.FORBIDDEN);
        }
        Book book = bookRepository.findByTitle(title).orElseThrow(()->new NotFoundException("Book", title));
        Cart cart = cartRepository.findByPersonEmail(personEmail).orElseThrow(()->new NotFoundException("Cart", personEmail));
        boolean isAdded = cart.addToCart(book);
        return ResponseEntity.ok(cartAssembler.toModel(cartRepository.save(cart)));




    }

    @NotNull
    private boolean isAdminOrDataOwner(String personEmail) {
        boolean isModifyingOwnData = authenticationFacade.isModifingOwnData(personEmail);
        boolean isAdmin = authenticationFacade.isAdmin();
        return isAdmin || isModifyingOwnData;
    }
}
