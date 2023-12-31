package com.diegoczajka.reactiveprogrammin.services;

import com.diegoczajka.reactiveprogrammin.domain.Book;
import com.diegoczajka.reactiveprogrammin.domain.Review;
import com.diegoczajka.reactiveprogrammin.exception.BookException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

import java.time.Duration;
import java.util.List;

@Slf4j
public class BookService {

    private BookInfoService bookInfoService;
    private ReviewService reviewService;

    public BookService(BookInfoService bookInfoService, ReviewService reviewService) {
        this.bookInfoService = bookInfoService;
        this.reviewService = reviewService;
    }

    public Flux<Book> getBooks() {
        var allBooks = bookInfoService.getBooks();
        return allBooks.flatMap(bookInfo -> {
                    Mono<List<Review>> reviews
                            = reviewService.getReviews(bookInfo.getBookId()).collectList();
                    return reviews.map(
                            review -> new Book(bookInfo, review));
                }).onErrorMap(throwable -> {
                    log.error("Exception is: " + throwable);
                    return new BookException("Exception occured while fetching books " + throwable);
                }).retry(3).
                log();

    }

    public Flux<Book> getBooksRetryWhen() {
        var retrySpecs = getRetryBackoffSpec();
        var allBooks = bookInfoService.getBooks();
        return allBooks.flatMap(bookInfo -> {
                    Mono<List<Review>> reviews
                            = reviewService.getReviews(bookInfo.getBookId()).collectList();
                    return reviews.map(
                            review -> new Book(bookInfo, review));
                }).onErrorMap(throwable -> {
                    log.error("Exception is: " + throwable);
                    return new BookException("Exception occured while fetching books " + throwable);
                }).retryWhen(retrySpecs).
                log();

    }

    private static RetryBackoffSpec getRetryBackoffSpec() {
        return Retry.
                backoff(
                        3,
                        Duration.ofMillis(1000))
                .onRetryExhaustedThrow((retryBackOffSpec, retrySignal) ->
                        Exceptions.propagate(retrySignal.failure())
                ).filter(throwable -> throwable instanceof BookException);
    }

    public Mono<Book> getBookId(long bookId) {
        var book = bookInfoService.getBookById(bookId);
        var review = reviewService.getReviews(bookId).collectList();

        return book.zipWith(review, (b, r) -> new Book(b, r)).log();
    }
}
