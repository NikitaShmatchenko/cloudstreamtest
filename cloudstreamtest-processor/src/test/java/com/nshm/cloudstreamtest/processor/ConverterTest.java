package com.nshm.cloudstreamtest.processor;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ConverterTest {

    private static final String INPUT_TOPIC = "input_topic";
    private static final String OUTPUT_TOPIC = "output_topic";
    private static final String GROUP_NAME = "test";
    private static final String MESSAGE = "abc";
    private static final String MODIFIED_PREFIX = "-processed";

    @ClassRule
    public static EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, true, OUTPUT_TOPIC);

    @BeforeClass
    public static void setup() {
        System.setProperty("spring.cloud.stream.kafka.binder.brokers", embeddedKafka.getEmbeddedKafka().getBrokersAsString());
    }

    @Test
    public void convert() {
        Map<String, Object> senderProps = KafkaTestUtils.producerProps(embeddedKafka.getEmbeddedKafka());
        DefaultKafkaProducerFactory<byte[], String> pf = new DefaultKafkaProducerFactory<>(senderProps);
        KafkaTemplate<byte[], String> template = new KafkaTemplate<>(pf, true);
        template.setDefaultTopic(INPUT_TOPIC);
        template.sendDefault(MESSAGE);

        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps(GROUP_NAME, "false", embeddedKafka.getEmbeddedKafka());
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        DefaultKafkaConsumerFactory<byte[], String> cf = new DefaultKafkaConsumerFactory<>(consumerProps);

        Consumer<byte[], String> consumer = cf.createConsumer();
        consumer.subscribe(Collections.singleton(OUTPUT_TOPIC));
        ConsumerRecords<byte[], String> records = consumer.poll(Duration.ofSeconds(1));
        consumer.commitSync();

        assertEquals(records.count(), 1);
        assertEquals(records.iterator().next().value(), MESSAGE + MODIFIED_PREFIX);
    }
}
