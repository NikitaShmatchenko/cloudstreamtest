package com.nshm.cloudstreamtest.sink;

import com.nshm.cloudstreamtest.persisitence.entity.ProcessedString;
import com.nshm.cloudstreamtest.persisitence.repository.StringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.function.Consumer;

@EnableJpaRepositories("com.nshm.cloudstreamtest.persisitence.repository")
@EntityScan("com.nshm.cloudstreamtest.persisitence.entity")
@SpringBootApplication
public class Sink {

    @Autowired
    private StringRepository stringRepository;

    public static void main(String[] args) {
        SpringApplication.run(Sink.class, args);
    }

    @Bean
    public Consumer<String> persist() {
        return str ->
                stringRepository.save(ProcessedString.builder()
                        .processedString(str)
                        .build());
    }
}


