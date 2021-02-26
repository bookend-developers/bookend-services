package com.bookend.bookservice.service;

import com.bookend.bookservice.exception.AlreadyExist;
import com.bookend.bookservice.exception.MandatoryFieldException;
import com.bookend.bookservice.exception.NotFoundException;
import com.bookend.bookservice.kafka.Producer;
import com.bookend.bookservice.model.Genre;
import com.bookend.bookservice.model.KafkaMessage;
import com.bookend.bookservice.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService{
    private static final String GENRE_TOPIC = "adding-genre";
    private Producer producer;
    @Autowired
    public void setProducer(Producer producer){this.producer=producer;}
    private GenreRepository genreRepository;
    @Autowired
    public void setGenreRepository(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Genre findByGenre(String genre) {
        return genreRepository.findByGenre(genre);
    }

    @Override
    public Genre addNewGenre(String genre) throws AlreadyExist, MandatoryFieldException {
        if(genre == "" || genre == null){
            throw new MandatoryFieldException("Genre cannot be empty");
        }
        Genre newGenre = findByGenre(genre);
        if(newGenre==null){
           newGenre = new Genre(genre);

           newGenre = genreRepository.save(newGenre);
           KafkaMessage kafkaMessage = new KafkaMessage(GENRE_TOPIC,newGenre);
           producer.publishGenre(kafkaMessage);

           return newGenre;

        }
        else{
            throw new AlreadyExist("Genre already exists.");
        }
    }

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre findById(String id) throws NotFoundException {
        Genre genre = genreRepository.findGenreById(id);
        if(genre == null){
            throw new NotFoundException("No genre is match with given id");
        }
        return genre;
    }

    @Override
    public Genre update(Genre genre) throws MandatoryFieldException, NotFoundException, AlreadyExist {
        if(findByGenre(genre.getGenre())!=null){
            throw new AlreadyExist("Genre already exist");
        }
        Genre oldGenre = findById(genre.getId());
        if(genre.getGenre() == "" || genre.getGenre() == null){
            throw new MandatoryFieldException("Genre cannot be empty");
        }
        oldGenre.setGenre(genre.getGenre());
        KafkaMessage kafkaMessage = new KafkaMessage(GENRE_TOPIC,genre);
        producer.publishGenre(kafkaMessage);
        return genreRepository.save(oldGenre);
    }
}
