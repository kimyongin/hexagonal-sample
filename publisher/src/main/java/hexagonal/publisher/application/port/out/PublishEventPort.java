package hexagonal.publisher.application.port.out;

import hexagonal.publisher.domain.Event;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface PublishEventPort {
  Mono<Event> publishEvent(Event event);
}
