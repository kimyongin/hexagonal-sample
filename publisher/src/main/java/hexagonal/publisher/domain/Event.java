package hexagonal.publisher.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Event {
  private String id;
  private String type;
  private String message;

}
