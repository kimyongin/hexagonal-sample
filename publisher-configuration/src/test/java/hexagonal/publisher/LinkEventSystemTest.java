package hexagonal.publisher;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

//https://www.baeldung.com/spring-boot-kafka-testing
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class LinkEventSystemTest {

  @Autowired
  TestRestTemplate testRestTemplate;
  @Value(value = "${spring.kafka.topic}")
  private String topic;

  @Test
  void linkEvent() {
    HttpHeaders headers = new HttpHeaders();
    headers.add("Content-Type", "application/json");
    HttpEntity<String> request = new HttpEntity<>("hello", headers);

    ResponseEntity<String> exchange = testRestTemplate.exchange(
        "/event/link",
        HttpMethod.POST,
        request,
        String.class);
    String eventId = exchange.getBody();
    Assertions.assertNotNull(eventId);

    KafkaConsumer<String, String> consumer = kafkaConsumer();
    consumer.subscribe(Collections.singletonList(topic));
    ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(5));
    Assertions.assertEquals(1, records.count());
    for (ConsumerRecord<String, String> record : records) {
      Assertions.assertTrue(record.value().contains(eventId));
    }
  }

  public KafkaConsumer<String, String> kafkaConsumer(){
    // https://kafka.apache.org/documentation/#consumerconfigs_group.id
    Properties props = new Properties();
    props.setProperty("bootstrap.servers", "localhost:9092");
    props.setProperty("enable.auto.commit", "true");
    props.setProperty("auto.offset.reset", "earliest");
    props.setProperty("max.poll.interval.ms", "2000");
    props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    props.setProperty("group.id", "sample");

    return new KafkaConsumer<>(props);
  }
}
