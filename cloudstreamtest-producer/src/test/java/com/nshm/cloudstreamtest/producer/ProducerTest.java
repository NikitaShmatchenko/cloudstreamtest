package com.nshm.cloudstreamtest.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;


import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//TODO Investigate why message doesn't show up after submit is called.
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProducerTest {

    @Autowired
    private Producer producer;

    @Test
    public void productUUID() {
        String generatedValue = UUID.randomUUID().toString();

        try (ConfigurableApplicationContext context = new SpringApplicationBuilder(
                TestChannelBinderConfiguration.getCompleteConfiguration(
                        Producer.class))
                .run("--spring.cloud.function.definition=produce")) {

            producer.submit(generatedValue);

            OutputDestination target = context.getBean(OutputDestination.class);

            byte[] message = target.receive().getPayload();
            assertNotNull(message);
            String stringMessage = new String(message, StandardCharsets.UTF_8);
            assertEquals(stringMessage, generatedValue);
        }
    }
}
