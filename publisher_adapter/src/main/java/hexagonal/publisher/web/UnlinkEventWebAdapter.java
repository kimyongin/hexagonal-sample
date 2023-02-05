package hexagonal.publisher.web;


import hexagonal.publisher.application.port.in.UnlinkEventUseCase;
import hexagonal.publisher.domain.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class UnlinkEventWebAdapter {

  @Autowired
  private UnlinkEventUseCase unlinkEventUseCase;

  @PostMapping(path = "/event/unlink")
  public Mono<String> unlinkEvent(@RequestBody String message) {
    return unlinkEventUseCase.handleUnlinkEvent(message)
        .map(Event::getId);
  }

}
