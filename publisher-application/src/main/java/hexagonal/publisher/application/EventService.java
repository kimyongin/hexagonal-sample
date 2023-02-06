package hexagonal.publisher.application;

import hexagonal.publisher.domain.Event;
import hexagonal.publisher.port.in.LinkEventUseCase;
import hexagonal.publisher.port.in.UnlinkEventUseCase;
import hexagonal.publisher.port.out.PublishEventPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
class EventService implements LinkEventUseCase, UnlinkEventUseCase {

  @Autowired
  private PublishEventPort publishEventPort;

  @Override
  public Mono<Event> handleLinkEvent(String message) {
    Event linkEvent = Event.builder().type("link").message(message).build();
    return publishEventPort.publishEvent(linkEvent);
  }

  @Override
  public Mono<Event> handleUnlinkEvent(String message) {
    Event unlinkEvent = Event.builder().type("unlink").message(message).build();
    return publishEventPort.publishEvent(unlinkEvent);
  }
}
