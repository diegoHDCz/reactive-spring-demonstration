package com.diegoczajka.reactiveprogrammin.services;

import com.diegoczajka.reactiveprogrammin.domain.BookInfo;
import com.diegoczajka.reactiveprogrammin.exception.BookException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class BookServiceMockTest {

    @Mock
    private BookInfoService bookInfoService;
    @Mock
    private ReviewService reviewService;

    @InjectMocks
    private BookService bookService;

    @Test
    void getBooksMock() {
        Mockito.when(bookInfoService.getBooks()).thenCallRealMethod();
        Mockito.when(reviewService.getReviews(anyLong())).thenCallRealMethod();

        var books = bookService.getBooks();
        StepVerifier.create(books).expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void getBooksMockOnError() {
        Mockito.when(bookInfoService.getBooks()).thenCallRealMethod();
        Mockito.when(reviewService.getReviews(anyLong())).thenThrow(new IllegalStateException("excpetion using test"));


        var books = bookService.getBooks();
        StepVerifier.create(books).expectError(BookException.class)
                .verify();
    }

    @Test
    void getBooksMockOnErrorRetry() {
        Mockito.when(bookInfoService.getBooks()).thenCallRealMethod();
        Mockito.when(reviewService.getReviews(anyLong())).thenThrow(new IllegalStateException("excpetion using test"));


        var books = bookService.getBooks();
        StepVerifier.create(books).expectError(BookException.class)
                .verify();
    }

    @Test
    void getBooksMockOnErrorRetrWhen() {
        Mockito.when(bookInfoService.getBooks()).thenCallRealMethod();
        Mockito.when(reviewService.getReviews(anyLong())).thenThrow(new IllegalStateException("excpetion using test"));


        var books = bookService.getBooksRetryWhen();
        StepVerifier.create(books).expectError(BookException.class)
                .verify();
    }
}