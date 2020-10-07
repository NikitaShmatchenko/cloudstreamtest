package com.nshm.cloudstreamtest.sink;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

@SpringBootApplication
public class Sink {
    public static void main(String[] args) {
        SpringApplication.run(Sink.class, args);
    }

    @Bean
    public Consumer<String> print() {
        return System.out::println;
    }
}


