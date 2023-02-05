package hexagonal.publisher.adapter.out.stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexagonal.publisher.application.port.out.PublishEventPort;
import hexagonal.publisher.domain.Event;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class EventPublisher implements PublishEventPort {

  @Autowired
  KafkaTemplate<String, String> kafkaTemplate;
  @Value(value = "${spring.kafka.topic}")
  private String topic;

  ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public Mono<Event> publishEvent(Event event) {
    event.setId(UUID.randomUUID().toString());

    String message;
    try {
      message = objectMapper.writeValueAsString(event);
    } catch (JsonProcessingException e) {
      return Mono.error(e);
    }
    return Mono.fromFuture(kafkaTemplate.send(topic, message))
        .thenReturn(event);
  }
}
