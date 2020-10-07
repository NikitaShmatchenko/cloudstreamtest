package com.nshm.cloudstreamtest.producer;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;
import java.util.function.Supplier;

@SpringBootApplication
public class Producer {

    public static void main(String[] args) {
        SpringApplication.run(Producer.class, args);
    }

    @Bean
    public Supplier<String> generate() {
        return () -> UUID.randomUUID().toString();
    }
}
