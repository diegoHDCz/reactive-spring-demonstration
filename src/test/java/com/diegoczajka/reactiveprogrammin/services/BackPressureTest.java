package com.diegoczajka.reactiveprogrammin.services;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

public class BackPressureTest {

    @Test
    public void testBackPressure() {
        var numbers = Flux.range(1, 100).log();
        numbers.subscribe(integer ->
                System.out.println("integer " + integer));
        numbers.subscribe(new BaseSubscriber<Integer>() {
            @Override
            protected Subscription upstream() {
                return super.upstream();
            }

            @Override
            public boolean isDisposed() {
                return super.isDisposed();
            }

            @Override
            public void dispose() {
                super.dispose();
            }

            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                request(3);
            }

            @Override
            protected void hookOnNext(Integer value) {
                System.out.println("value = " + value);
                if (value == 3) cancel();
            }

            @Override
            protected void hookOnComplete() {
                System.out.println("Competed");
            }

            @Override
            protected void hookOnError(Throwable throwable) {
                super.hookOnError(throwable);
            }

            @Override
            protected void hookOnCancel() {
                super.hookOnCancel();
            }

            @Override
            protected void hookFinally(SignalType type) {
                super.hookFinally(type);
            }

            @Override
            public String toString() {
                return super.toString();
            }
        });
    }

    @Test
    public void testBackPressureDrop() {
        var numbers = Flux.range(1, 100).log();
//        numbers.subscribe(integer ->
//                System.out.println("integer " + integer));
        numbers.onBackpressureDrop(integer -> System.out.println("Drop value " + integer))
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected Subscription upstream() {
                        return super.upstream();
                    }

                    @Override
                    public boolean isDisposed() {
                        return super.isDisposed();
                    }

                    @Override
                    public void dispose() {
                        super.dispose();
                    }

                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(3);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        System.out.println("value = " + value);
                        if (value == 3) hookOnCancel();
                    }

                    @Override
                    protected void hookOnComplete() {
                        System.out.println("Competed");
                    }

                    @Override
                    protected void hookOnError(Throwable throwable) {
                        super.hookOnError(throwable);
                    }

                    @Override
                    protected void hookOnCancel() {
                        super.hookOnCancel();
                    }

                    @Override
                    protected void hookFinally(SignalType type) {
                        super.hookFinally(type);
                    }

                    @Override
                    public String toString() {
                        return super.toString();
                    }
                });
    }

    @Test
    public void testBackPressureBuffer() {
        var numbers = Flux.range(1, 100).log();
//        numbers.subscribe(integer ->
//                System.out.println("integer " + integer));
        numbers.onBackpressureBuffer(10,
                        integer -> System.out.println("Drop value " + integer))
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected Subscription upstream() {
                        return super.upstream();
                    }

                    @Override
                    public boolean isDisposed() {
                        return super.isDisposed();
                    }

                    @Override
                    public void dispose() {
                        super.dispose();
                    }

                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(3);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        System.out.println("value = " + value);
                        if (value == 3) hookOnCancel();
                    }

                    @Override
                    protected void hookOnComplete() {
                        System.out.println("Competed");
                    }

                    @Override
                    protected void hookOnError(Throwable throwable) {
                        super.hookOnError(throwable);
                    }

                    @Override
                    protected void hookOnCancel() {
                        super.hookOnCancel();
                    }

                    @Override
                    protected void hookFinally(SignalType type) {
                        super.hookFinally(type);
                    }

                    @Override
                    public String toString() {
                        return super.toString();
                    }
                });


    }

    @Test
    public void testBackPressureError() {
        var numbers = Flux.range(1, 100).log();
//        numbers.subscribe(integer ->
//                System.out.println("integer " + integer));
        numbers.onBackpressureError()
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected Subscription upstream() {
                        return super.upstream();
                    }

                    @Override
                    public boolean isDisposed() {
                        return super.isDisposed();
                    }

                    @Override
                    public void dispose() {
                        super.dispose();
                    }

                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(3);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        System.out.println("value = " + value);
                        if (value == 3) hookOnCancel();
                    }

                    @Override
                    protected void hookOnComplete() {
                        System.out.println("Competed");
                    }

                    @Override
                    protected void hookOnError(Throwable throwable) {
                        System.out.println("throwable = " + throwable);
                    }

                    @Override
                    protected void hookOnCancel() {
                        super.hookOnCancel();
                    }

                    @Override
                    protected void hookFinally(SignalType type) {
                        super.hookFinally(type);
                    }

                    @Override
                    public String toString() {
                        return super.toString();
                    }
                });

    }
}

