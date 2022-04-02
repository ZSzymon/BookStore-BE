package com.assigment.bookstore.book;


import com.assigment.bookstore.exceptions.NotFoundException;
import com.assigment.bookstore.person.Person;
import com.assigment.bookstore.person.PersonRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookModelAssembler bookAssembler;
    private final PersonRepository personRepository;

    public CollectionModel<EntityModel<BookDTO>> all() {
        log.info("Returning all books");
        return bookAssembler.toCollectionModel(bookRepository.findAll());
    }

    public ResponseEntity<EntityModel<BookDTO>> getByTitle(String title) {
        log.info("Getting book:<"+title+">");
        return bookRepository.findByTitle(title)
                .map(bookAssembler::toModel).map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundException("Book", title));

    }

    public ResponseEntity<?> removeOne(String title){
        bookRepository.removeBookByTitle(title);
        return ResponseEntity.ok("");
    }

    public ResponseEntity<EntityModel<BookDTO>> addOne(@Valid BookDTO bookDTO) {
        //if person is found then return response with 203 status (See other)
        //else create new book and return 201. CREATED status.
        return bookRepository.findByTitle(bookDTO.getTitle()).
                map(p -> {
                    log.info("Adding failed."+bookDTO.getTitle() +" Book already exist");
                    return new ResponseEntity<>(bookAssembler.toModel(p), HttpStatus.SEE_OTHER);
                })
                .orElseGet(() ->
                {
                    log.info("Creating new book: "+ bookDTO.getTitle());
                    Person publisher = personRepository.findByEmail(bookDTO.getPublisher().getEmail())
                            .orElse(personRepository.insert(new Person(bookDTO.getPublisher().getEmail())));
                    Person author = personRepository.findByEmail(bookDTO.getAuthor().getEmail())
                            .orElse(personRepository.insert(new Person(bookDTO.getAuthor().getEmail())));

                    Book newPerson = new Book(bookDTO.getTitle(), author, publisher);
                    EntityModel<BookDTO> entityModel = bookAssembler.toModel(bookRepository.insert(newPerson));
                    return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
                });

    }

}
