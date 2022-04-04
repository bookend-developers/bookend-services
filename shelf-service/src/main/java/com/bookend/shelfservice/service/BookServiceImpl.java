package com.bookend.shelfservice.service;

import com.bookend.shelfservice.exception.AlreadyExists;
import com.bookend.shelfservice.exception.NotFoundException;
import com.bookend.shelfservice.exception.ShelfsBookNotFound;
import com.bookend.shelfservice.model.Shelf;
import com.bookend.shelfservice.model.ShelfsBook;
import com.bookend.shelfservice.payload.BookRequest;
import com.bookend.shelfservice.repository.BookRepository;
import com.bookend.shelfservice.repository.ShelfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;
    private ShelfRepository shelfRepository;
    @Autowired
    public void setShelfRepository(ShelfRepository shelfRepository) {
        this.shelfRepository = shelfRepository;
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository){
        this.bookRepository=bookRepository;
    }
    @Override
    public ShelfsBook getById(String id) throws ShelfsBookNotFound {
        ShelfsBook shelfsBook = bookRepository.findBookById(Long.valueOf(id));
        if(shelfsBook == null){
            throw new ShelfsBookNotFound("Shelf's books not found..");
        }
        return shelfsBook;
    }


    @Override
    public ShelfsBook saveOrUpdate(BookRequest book, Shelf shelf) throws AlreadyExists {
        ShelfsBook shelfsBook = new ShelfsBook(book.getBookid(),book.getBookName(),shelf);
        List<ShelfsBook> books = bookRepository.findShelfsBookByShelf(shelfsBook.getShelf());
        if(books.stream().anyMatch(b -> b.getBookID().matches(shelfsBook.getBookID()))){
            throw new AlreadyExists("The book is already added this shelf");
        }
        return bookRepository.save(shelfsBook);
    }

    @Override
    public void delete(String bookId,String shelfID) throws NotFoundException {
        Shelf shelf = shelfRepository.findShelfById(Long.valueOf(shelfID));
        if(shelf==null){
            throw new NotFoundException("Shelf does not exist");
        }
        ShelfsBook shelfsBook = bookRepository.findByBookIDAndShelf(bookId,shelf);
        if(shelfsBook == null){
            throw new NotFoundException("Shelf's book does not exist");
        }

        bookRepository.delete(shelfsBook);
    }

    @Override
    public void deleteFromShelves(String bookid) throws NotFoundException {
        List<ShelfsBook> shelfsBooks = bookRepository.findShelfsBookByBookID(bookid);
        if(shelfsBooks == null && shelfsBooks.isEmpty()){
            throw new NotFoundException("The list of shelfs books does not exist");
        }
        shelfsBooks.forEach(shelfsBook -> bookRepository.delete(shelfsBook));
    }
}
//5f7a1001caa59177cb3cd96e