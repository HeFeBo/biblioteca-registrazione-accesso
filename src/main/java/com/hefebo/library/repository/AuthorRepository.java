package com.hefebo.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hefebo.library.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
