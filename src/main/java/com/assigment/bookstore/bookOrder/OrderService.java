package com.assigment.bookstore.bookOrder;

import com.assigment.bookstore.generics.IGenericService;
import com.assigment.bookstore.book.Book;
import com.assigment.bookstore.book.BookRepository;
import com.assigment.bookstore.exceptions.NotFoundException;
import com.assigment.bookstore.bookOrder.models.BookOrder;
import com.assigment.bookstore.bookOrder.models.BookOrderDto;
import com.assigment.bookstore.securityJwt.authenticationFacade.AuthenticationFacade;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService implements IGenericService<BookOrder, BookOrderDto> {

    BookOrderRepository bookOrderRepository;
    BookRepository bookRepository;
    BookOrderAssembler modelAssembler;
    AuthenticationFacade authenticationFacade;
    @Override
    public CollectionModel<?> all() {
        return modelAssembler.toCollectionModel(bookOrderRepository.findAll());
    }

    @Override
    public ResponseEntity<?> getBy(String name) {
        return ResponseEntity.ok(modelAssembler.toCollectionModel(bookOrderRepository
                .findAllByClientEmail(name)));
    }

    @Override
    public ResponseEntity<?> removeOne(String name) {
        bookOrderRepository.removeOrderByClientEmail(name);
        return ResponseEntity.ok("");
    }

    @Override
    public ResponseEntity<?> addOne(BookOrderDto bookOrderDto) {
        String email = authenticationFacade.getUserDetailsImpl().getEmail();
        return addOne(bookOrderDto, email);
    }

    public ResponseEntity<?> addOne(BookOrderDto bookOrderDto, String email) {

        if (bookOrderDto.getBookList().isEmpty()) {
            return ResponseEntity.badRequest().body("Empty order.");
        }
        List<Book> booksList = bookOrderDto.getBookList().stream()
                .map(id -> bookRepository.findById(id)
                        .orElseThrow(()->new NotFoundException("Book", id)))
                .toList();
        BookOrder bookOrder = bookOrderRepository.insert(new BookOrder(booksList, email, bookOrderDto.getDescription()));
        return new ResponseEntity<>(modelAssembler.toModel(bookOrder), HttpStatus.CREATED);

    }
}
