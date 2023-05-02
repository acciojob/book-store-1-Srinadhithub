package com.driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("books")
public class BookController {

    private List<Book> bookList;
    private int id;
    private HashMap<Integer,Book> bookHashMap= new HashMap<>();

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookController(){
        this.bookList = new ArrayList<Book>();
        this.id = 1;
    }

    // post request /create-book
    // pass book as request body
    @PostMapping("/create-book")
    public ResponseEntity<Book> createBook(@RequestBody Book book){
        // Your code goes here.
        book.setId(id);
        id++;
        bookList.add(book);
        bookHashMap.put(book.getId(),book);
        return new ResponseEntity<>(book, HttpStatus.CREATED);
    }

    // get request /get-book-by-id/{id}
    // pass id as path variable
    @GetMapping("/get-book-by-id/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id){
        return new ResponseEntity<>(bookHashMap.get(id),HttpStatus.FOUND);
    }

    // delete request /delete-book-by-id/{id}
    // pass id as path variable
    @DeleteMapping("/delete-book-by-id/{id}")
    public ResponseEntity deleteBookById(@PathVariable int id){
        Book book=bookHashMap.get(id);
        if(book==null) return new ResponseEntity<>("Book not found",HttpStatus.NOT_FOUND);
        bookList.remove(book);
        bookHashMap.remove(id);
        return new ResponseEntity("Successfully deleted",HttpStatus.FOUND);

    }
    @GetMapping("/get-all-books")
    public ResponseEntity getAllBooks(){
        return new ResponseEntity(bookList,HttpStatus.OK);
    }
   @DeleteMapping("/delete-all-books")
    public ResponseEntity deleteAllBooks(){
        bookList.clear();
        bookHashMap.clear();
        return new ResponseEntity("Successfully deleted",HttpStatus.OK);
   }
    @GetMapping("/get-books-by-author")
    public ResponseEntity getBooksByAuthor(@RequestParam String author){
        List<Book> list=new ArrayList<>();
        for(Book book : bookList){
            if(author.equals(book.getAuthor())){
                list.add(book);
            }
        }
        return new ResponseEntity(list,HttpStatus.FOUND);
    }

    @GetMapping("/get-books-by-genre")
    public ResponseEntity getBooksByGenre(@RequestParam String genre){
        List<Book> list=new ArrayList<>();
        for(Book book : bookList){
            if(genre.equals(book.getGenre())){
                list.add(book);
            }
        }
        return new ResponseEntity(list,HttpStatus.FOUND);
    }
}
