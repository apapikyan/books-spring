package books.controllers;

import books.models.Book;
import books.DefaultBookService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/book")
public class BookController {

    private final static Logger logger = Logger.getLogger(BookController.class);

    @Autowired //TODO use @Inject - why ?
            DefaultBookService bookService;

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        bookService.save(book);
        logger.debug("Added:: " + book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> updateBook(@RequestBody Book book) {
        Optional<Book> existingBook = Optional.ofNullable(bookService.getById(book.getId()));
        if (!existingBook.isPresent()) {
            logger.debug("Book with id " + book.getId() + " doesn't exist");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            bookService.save(book);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Book> getBook(@PathVariable("id") Long id) {
        Optional<Book> book = Optional.ofNullable(bookService.getById(id));
        if (!book.isPresent()) {
            logger.debug("Book with id " + id + " doesn't exist");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.debug("Found Book:: " + book);
        return new ResponseEntity<>(book.get(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAll();
        if (books.isEmpty()) {
            logger.debug("Books do not exist");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        logger.debug("Found " + books.size() + " Employees");
        logger.debug(books);
        logger.debug(Arrays.toString(books.toArray()));
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
        Optional<Book> book = Optional.ofNullable(bookService.getById(id));
        if (!book.isPresent()) {
            logger.debug("Book with id " + id + "doesn't exist");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            bookService.delete(book.get().getId());
            return new ResponseEntity<>(HttpStatus.GONE);
        }
    }


}
