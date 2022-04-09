package com.assigment.bookstore.book;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BookRepository extends MongoRepository<Book, String> {
    Optional<Book> findByTitle(String title);
    void removeBookByTitle(String title);

}