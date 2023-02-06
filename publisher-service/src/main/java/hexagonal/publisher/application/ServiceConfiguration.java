package hexagonal.publisher.application;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = "hexagonal.publisher.*")
public class ServiceConfiguration {

}
