package com.nshm.cloudstreamtest.sink;

import com.nshm.cloudstreamtest.persisitence.entity.ProcessedString;
import com.nshm.cloudstreamtest.persisitence.repository.StringRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.mockito.Mockito.verify;
//TODO Fix input destination getting NullPointerException when trying to send a message.
@SpringBootTest
@RunWith(SpringRunner.class)
public class SinkTest {

    @Mock
    StringRepository stringRepository;

    @Test
    public void productUUID() {
        String generatedValue = UUID.randomUUID().toString();

        ProcessedString processedString = ProcessedString.builder()
                .processedString(generatedValue)
                .build();

        try (ConfigurableApplicationContext context = new SpringApplicationBuilder(
                TestChannelBinderConfiguration.getCompleteConfiguration(
                        Sink.class))
                .run("--spring.cloud.function.definition=persist")) {

            Message<byte[]> inputMessage = MessageBuilder.withPayload(generatedValue.getBytes()).build();
            InputDestination inputDestination = context.getBean(InputDestination.class);
            inputDestination.send(inputMessage, "persist-in-0");

            verify(stringRepository).save(processedString);
        }
    }
}
