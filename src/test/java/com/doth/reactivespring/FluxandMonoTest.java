package com.doth.reactivespring;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxandMonoTest {
    @Test
    public void fluxTest(){
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new  RuntimeException("Runtime Error")))
                .log();
        stringFlux
                .subscribe(System.out::println,
                        (e) -> System.err.println(e));
    }

    @Test
    public void fluxTest_WithoutError(){
        Flux<String> stringFlux = Flux.just("Spring", "Sprint Boot", "Reactive Spring")
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring")
                .expectNext("Sprint Boot")
                .expectNext("Reactive Spring")
                .verifyComplete();

    }

    @Test
    public void fluxTest_WithError(){
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Runtime error encountered")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNext("Spring", "Spring Boot", "Reactive Spring")
                .expectError()
                //.expectErrorMessage("Runtime error encountered")
                .verify();
    }

    @Test
    public void fluxTestElementCount_WithError(){
        Flux<String> stringFlux = Flux.just("Spring", "Spring Boot", "Reactive Spring")
                .concatWith(Flux.error(new RuntimeException("Runtime error encountered")))
                .log();

        StepVerifier.create(stringFlux)
                .expectNextCount(3)
                .expectError()
                //.expectErrorMessage("Runtime error encountered")
                .verify();
    }

    @Test
    public void monoTest(){
        Mono<String> stringMono = Mono.just("Spring");

        StepVerifier.create(stringMono.log())
                .expectNext("Spring")
                .verifyComplete();
    }

    @Test
    public void monoTest_WithError(){

        StepVerifier.create(Mono.error(new RuntimeException("Runtime error encountered")).log())
                .expectError(RuntimeException.class)
                .verify();
    }

}
