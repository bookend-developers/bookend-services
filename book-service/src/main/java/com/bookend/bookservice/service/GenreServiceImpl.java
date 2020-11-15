package com.bookend.bookservice.service;

import com.bookend.bookservice.kafka.Producer;
import com.bookend.bookservice.model.Genre;
import com.bookend.bookservice.model.KafkaMessage;
import com.bookend.bookservice.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Genre addNewGenre(String genre) {

        Genre newGenre = findByGenre(genre);
        if(newGenre==null){
           newGenre = new Genre(genre);
           KafkaMessage kafkaMessage = new KafkaMessage(GENRE_TOPIC,newGenre);
           producer.publishGenre(kafkaMessage);
           genreRepository.save(newGenre);
           return newGenre;

        }
        return null;
    }
}
