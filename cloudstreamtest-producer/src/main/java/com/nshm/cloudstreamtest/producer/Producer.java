package com.nshm.cloudstreamtest.producer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Producer {

    private static final String BINDING_NAME = "produce-out-0";

    @Autowired
    private StreamBridge streamBridge;

    public static void main(String[] args) {
        SpringApplication.run(Producer.class, args);
    }

    @PostMapping("/string/{value}")
    public String submit(@PathVariable String value) {
        streamBridge.send(BINDING_NAME, value);
        return "String" + value + "has been published.";
    }
}
