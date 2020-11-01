package com.ratecommentservice.repository;

import com.ratecommentservice.model.Book;
import com.ratecommentservice.model.Rate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface RateRepository extends JpaRepository<Rate,Long> {
    List<Rate> findByUsername(String userId);
    List<Rate> findByBook(Book book);
    Rate findByRateId(Long rateId);
    Rate findByBookAndUsername(Book book,String username);
}
