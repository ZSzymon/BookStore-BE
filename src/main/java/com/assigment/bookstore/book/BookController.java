package com.assigment.bookstore.book;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/books")
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping()
    public CollectionModel<EntityModel<BookDTO>> all(){
        return bookService.all();
    }

    @GetMapping("{title}")
    public ResponseEntity<EntityModel<BookDTO>> one(@PathVariable String title) {
        return bookService.getByTitle(title);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<EntityModel<BookDTO>> addOne(@RequestBody BookDTO book){
        return bookService.addOne(book);
    }

    @DeleteMapping("{name}")
    public ResponseEntity<?> removeOne(@PathVariable String name){
        return bookService.removeOne(name);
    }
}
