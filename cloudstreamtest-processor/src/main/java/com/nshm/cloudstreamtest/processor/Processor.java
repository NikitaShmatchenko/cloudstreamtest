package com.nshm.cloudstreamtest.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@SpringBootApplication
public class Processor {

    private static final String POSTFIX = "-processed";

    public static void main(String[] args) {
        SpringApplication.run(Processor.class, args);
    }

    @Bean
    public Function<String, String> reverse() {
        return str -> str + POSTFIX;
    }
}

