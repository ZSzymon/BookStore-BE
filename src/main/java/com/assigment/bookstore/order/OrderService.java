package com.assigment.bookstore.order;

import com.assigment.bookstore.generics.IGenericService;
import com.assigment.bookstore.book.Book;
import com.assigment.bookstore.book.BookRepository;
import com.assigment.bookstore.exceptions.NotFoundException;
import com.assigment.bookstore.order.models.Order;
import com.assigment.bookstore.order.models.OrderDto;
import com.assigment.bookstore.securityJwt.authenticationFacade.AuthenticationFacade;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService implements IGenericService<Order, OrderDto> {

    OrderRepository orderRepository;
    BookRepository bookRepository;
    OrderAssembler modelAssembler;
    AuthenticationFacade authenticationFacade;
    @Override
    public CollectionModel<?> all() {
        return modelAssembler.toCollectionModel(orderRepository.findAll());
    }

    @Override
    public ResponseEntity<?> getBy(String name) {
        return ResponseEntity.ok(modelAssembler.toCollectionModel(orderRepository
                .findAllByClientEmail(name)));
    }

    @Override
    public ResponseEntity<?> removeOne(String name) {
        orderRepository.removeOrderByClientEmail(name);
        return ResponseEntity.ok("");
    }

    @Override
    public ResponseEntity<?> addOne(OrderDto orderDto) {
        String email = authenticationFacade.getUserDetailsImpl().getEmail();
        return addOne(orderDto, email);
    }

    public ResponseEntity<?> addOne(OrderDto orderDto, String email) {

        if (orderDto.getBookList().isEmpty()) {
            return ResponseEntity.badRequest().body("Empty order.");
        }
        List<Book> booksList = orderDto.getBookList().stream()
                .map(id -> bookRepository.findById(id)
                        .orElseThrow(()->new NotFoundException("Book", id)))
                .toList();
        Order order = orderRepository.insert(new Order(booksList, email, orderDto.getDescription()));
        return new ResponseEntity<>(modelAssembler.toModel(order), HttpStatus.CREATED);

    }
}
