package com.nshm.cloudstreamtest.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;


import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProducerTest {

    private static final int UUID_LENGTH = 36;

    @Test
    public void productUUID() {
        try (ConfigurableApplicationContext context = new SpringApplicationBuilder(
                TestChannelBinderConfiguration.getCompleteConfiguration(
                        Producer.class))
                .run("--spring.cloud.function.definition=generate")) {
            OutputDestination target = context.getBean(OutputDestination.class);

            byte[] message = target.receive().getPayload();
            assertNotNull(message);
            String stringMessage = new String(message, StandardCharsets.UTF_8);
            assertEquals(stringMessage.length(), UUID_LENGTH);
        }
    }
}
