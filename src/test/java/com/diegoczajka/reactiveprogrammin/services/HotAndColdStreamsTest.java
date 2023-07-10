package com.diegoczajka.reactiveprogrammin.services;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class HotAndColdStreamsTest {

    @Test
    public void coldStreamTest() {
        var numbers = Flux.range(1, 10);

        numbers.subscribe(i -> System.out.println("Subscriber 1 =" + i));
        numbers.subscribe(i -> System.out.println("Subscriber 2 =" + i));

    }

    @Test
    public void hotStreamTest() throws InterruptedException {
        var numbers = Flux.range(1, 10)
                .delayElements(Duration.ofMillis(1000));

        ConnectableFlux<Integer> publisher = numbers.publish();
        publisher.connect();

        numbers.subscribe(i -> System.out.println("Subscriber 1 =" + i));
        Thread.sleep(4000);
        numbers.subscribe(i -> System.out.println("Subscriber 2 =" + i));
        Thread.sleep(10000);
    }


}
