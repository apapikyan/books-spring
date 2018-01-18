package books;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/book")
public class BookController {

    final static Logger logger = Logger.getLogger(BookController.class);

    @Autowired
    DefaultBookService bookService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        bookService.save(book);
        logger.debug("Added:: " + book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Void> updateBook(@RequestBody Book book) {
        Book existingBook = bookService.getById(book.getId());
        if (existingBook == null) {
            logger.debug("Book with id " + book.getId() + " doesn't exist");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        } else {
            bookService.save(book);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Book> getBook(@PathVariable("id") Long id) {
        Book book = bookService.getById(id);
        if (book == null) {
            logger.debug("Book with id " + book.getId() + " doesn't exist");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        logger.debug("Found Book:: " + book);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
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

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long id) {
        Book book = bookService.getById(id);
        if (book == null) {
            logger.debug("Book with id " + id + "doesn't exist");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            bookService.delete(book.getId());
            return new ResponseEntity<>(HttpStatus.GONE);
        }
    }


}
