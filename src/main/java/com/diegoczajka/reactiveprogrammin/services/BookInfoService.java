package com.diegoczajka.reactiveprogrammin.services;

import com.diegoczajka.reactiveprogrammin.domain.Book;
import com.diegoczajka.reactiveprogrammin.domain.BookInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class BookInfoService {
    public Flux<BookInfo> getBooks() {
        var books = List.of(
                new BookInfo(1, "Book One", "Author One", "1212121212"),
                new BookInfo(2, "Book Two", "Author Two", "4324324324"),
                new BookInfo(1, "Book Three", "Author Three", "1212121212")
        );
        return Flux.fromIterable(books);


    }

    public Mono<BookInfo> getBookById(long bookId) {
        var book = new BookInfo(bookId, "Book One", "Author One", "1212121212");
        return Mono.just(book);
    }

}

