package hexagonal.publisher;

import hexagonal.publisher.config.KafkaConsumerConfig;
import java.time.Duration;
import java.util.Collections;
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
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
@Import({KafkaConsumerConfig.class})
public class LinkEventSystemTest {

  @Autowired
  TestRestTemplate testRestTemplate;
  @Autowired
  KafkaConsumer<String, String> kafkaConsumer;
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

    kafkaConsumer.subscribe(Collections.singletonList(topic));
    ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(5));
    Assertions.assertEquals(1, records.count());
    for (ConsumerRecord<String, String> record : records) {
      Assertions.assertTrue(record.value().contains(eventId));
    }
  }
}
