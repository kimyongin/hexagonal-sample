package hexagonal.publisher.config;

import java.util.Properties;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConsumerConfig {

  @Bean
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
