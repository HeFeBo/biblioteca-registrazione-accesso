package com.hefebo.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hefebo.library.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
