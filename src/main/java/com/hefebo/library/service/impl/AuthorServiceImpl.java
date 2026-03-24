package com.hefebo.library.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hefebo.library.dto.request.AuthorRequest;
import com.hefebo.library.dto.response.AuthorResponse;
import com.hefebo.library.exception.AuthorNotFoundException;
import com.hefebo.library.mapper.AuthorMapper;
import com.hefebo.library.model.Author;
import com.hefebo.library.repository.AuthorRepository;
import com.hefebo.library.service.AuthorService;

@Service
public class AuthorServiceImpl implements AuthorService{
    private final AuthorRepository authorRepo;

    public AuthorServiceImpl(AuthorRepository authorRepo){
        this.authorRepo = authorRepo;
    }

    @Override
    public List<AuthorResponse> showAuthors() {
        return authorRepo.findAll().stream().map(a -> AuthorMapper.toDto(a)).toList();
    }

    @Override
    public AuthorResponse searchAuthor(long idAuthor) {
      Author author = authorRepo.findById(idAuthor).orElseThrow(() -> new AuthorNotFoundException("The author with ID " + idAuthor + " was not found."));
        return AuthorMapper.toDto(author);
    }

    @Override
    public AuthorResponse addAuthor(AuthorRequest dto) {
        Author author = AuthorMapper.toEntity(dto);
        Author savedAuthor = authorRepo.save(author);

        return AuthorMapper.toDto(savedAuthor);
       
    }

    @Override
    public AuthorResponse updateAuthor(AuthorRequest dto, long idAuthor) {
        Author author = authorRepo.findById(idAuthor).orElseThrow(() -> new AuthorNotFoundException("The author with ID " + idAuthor + " was not found."));
        author.setName(dto.getName());
        authorRepo.save(author);
        return AuthorMapper.toDto(author);
    }

    @Override
    public void deleteAuthor(long idAuthor) {
        if(!authorRepo.existsById(idAuthor)) throw new AuthorNotFoundException("The author with ID " + idAuthor + " was not found.");
        authorRepo.deleteById(idAuthor);
    } 

}
