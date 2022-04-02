package com.assigment.bookstore.book;


import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.stream.StreamSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * This class is used for generating links each Person object.
 */
@Component
public class BookModelAssembler implements RepresentationModelAssembler<Book, EntityModel<BookDTO>> {

    @Override
    public EntityModel<BookDTO> toModel(Book book){
        BookDTO bookDTO = new BookDTO(book);
        return EntityModel.of(bookDTO,
                linkTo(methodOn(BookController.class).one(book.getTitle())).withSelfRel(),
                linkTo(methodOn(BookController.class).all()).withRel("books"));
    }

    @Override
    public CollectionModel<EntityModel<BookDTO>> toCollectionModel(Iterable<? extends Book> entities) {
        var persons = StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModel).toList();
        return CollectionModel.of(persons,
                linkTo(methodOn(BookController.class).all()).withSelfRel());
    }
}
