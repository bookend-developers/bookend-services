package com.bookend.authorservice.service;

import com.bookend.authorservice.exception.AuthorAlreadyExists;
import com.bookend.authorservice.exception.MandatoryFieldException;
import com.bookend.authorservice.exception.AuthorNotFound;
import com.bookend.authorservice.model.Author;
import com.bookend.authorservice.payload.AuthorRequest;
import com.bookend.authorservice.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;
    @Autowired
    public void setAuthorRepository(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author getById(String id) throws AuthorNotFound {
        Author author = authorRepository.findAuthorById(id);
        if(author == null) {
            throw new AuthorNotFound("Author does not exist.");
        }
        return author;
    }

    @Override
    public Author update(Author author) {
        return authorRepository.save(author);
    }


    @Override
    public Author save(AuthorRequest request) throws AuthorAlreadyExists, MandatoryFieldException {
        Author author = new Author();
        if(request.getName()==null || request.getName() == ""){
            throw new MandatoryFieldException("Author's name cannot be empty.");
        }
        author.setName(request.getName());
        if(request.getBiography()==null || request.getBiography()== ""){
            throw new MandatoryFieldException("Author's biography cannot be empty.");
        }
        author.setBiography(request.getBiography());
        if(request.getBirthDate()==null || request.getBirthDate() == ""){
            throw new MandatoryFieldException("Author's birthday cannot be empty.");
        }
        author.setBirthDate(LocalDate.parse(request.getBirthDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US)));

        if(request.getDateOfDeath()=="" || request.getDateOfDeath() == null){
            author.setDateOfDeath(null);
        }else{
            author.setDateOfDeath(LocalDate.parse(request.getDateOfDeath(), DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US)));
        }

        List<Author> authors = authorRepository.findByName(author.getName());
        if(authors != null || authors.size()!=0){
            List<Author> filteredByBirth = authors.stream()
                    .filter(a -> a.getBirthDate().isEqual(author.getBirthDate()))
                    .collect(Collectors.toList());
            if(filteredByBirth.size()!=0){
                if((author.getDateOfDeath()!=null)){
                    List<Author> filteredByDeath = filteredByBirth.stream()
                            .filter(auth -> {
                                if(auth.getDateOfDeath()!=null){
                                    return auth.getDateOfDeath().isEqual(author.getDateOfDeath());
                                }else{
                                    return false;
                                }
                            })
                            .collect(Collectors.toList());
                    if(filteredByDeath.size()!=0){
                        throw new AuthorAlreadyExists("Author already exists.");
                    }
                }
                else{
                    List<Author> filteredByDeath = filteredByBirth.stream()
                            .filter(auth -> {
                                if(auth.getDateOfDeath()==null){
                                    return true;
                                }else{
                                    return false;
                                }
                            })
                            .collect(Collectors.toList());
                    if(filteredByDeath.size()!=0){
                        throw new AuthorAlreadyExists("Author already exists.");
                    }
                }
            }
        }

        return authorRepository.save(author);
    }


    @Override
    public List<Author> getAll() {


        List<Author> authors = authorRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
        return authors;
    }

    @Override
    public List<Author> search(String title) {
        return authorRepository.findByNameContainingIgnoreCase(title);
    }
}