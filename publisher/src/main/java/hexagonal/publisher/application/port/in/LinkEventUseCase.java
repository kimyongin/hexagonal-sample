package hexagonal.publisher.application.port.in;

import hexagonal.publisher.domain.Event;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public interface LinkEventUseCase {
  Mono<Event> handleLinkEvent(String message);
}
