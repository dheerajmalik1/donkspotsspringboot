package com.example.demo.controllerDonks;

import com.example.demo.modelDonks.Book;
import com.example.demo.repo.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    @Autowired
    private BookRepo bookRepo;

    @GetMapping("/getAll")
    public ResponseEntity<List<Book>> getAllBooks(){
    try{
        List<Book> bookList=new ArrayList<>();
        bookRepo.findAll().forEach(bookList::add);

        return new ResponseEntity<>(bookList,HttpStatus.OK);
    } catch(Exception e){
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }
    @GetMapping("/getBookById/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        Optional<Book> bookData=bookRepo.findById(id);
        if(bookData.isPresent()){
            return new ResponseEntity<>(bookData.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addBook")
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        Book bookObj=bookRepo.save(book);
        return new ResponseEntity<>(bookObj,HttpStatus.OK);
    }
    @PostMapping("/updateBookById/{id}")
    public ResponseEntity<Book> updateBookById(@PathVariable Long id, @RequestBody Book newBookData){
        Optional<Book> bookData =bookRepo.findById(id);
        if(bookData.isPresent()){
            Book book=bookData.get();
            book.setTitle(newBookData.getTitle());
            book.setAuthor(newBookData.getAuthor());
            Book bookObj=bookRepo.save(book);
            return new ResponseEntity<>(bookObj, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
