package hexagonal.publisher.adapter.in.web;


import hexagonal.publisher.application.port.in.LinkEventUseCase;
import hexagonal.publisher.domain.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class LinkEventWebAdapter {

  @Autowired
  private LinkEventUseCase linkEventUseCase;

  @PostMapping(path = "/event/link")
  public Mono<String> linkEvent(@RequestBody String message){
    return linkEventUseCase.handleLinkEvent(message)
        .map(Event::getId);
  }

}
