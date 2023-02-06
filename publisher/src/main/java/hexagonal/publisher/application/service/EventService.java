package hexagonal.publisher.application.service;

import hexagonal.publisher.adapter.in.web.LinkEventWebAdapter;
import hexagonal.publisher.application.port.in.LinkEventUseCase;
import hexagonal.publisher.application.port.in.UnlinkEventUseCase;
import hexagonal.publisher.application.port.out.PublishEventPort;
import hexagonal.publisher.domain.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class EventService implements LinkEventUseCase, UnlinkEventUseCase {

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
