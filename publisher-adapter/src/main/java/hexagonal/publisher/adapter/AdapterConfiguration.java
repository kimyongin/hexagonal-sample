package hexagonal.publisher.adapter;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = "hexagonal.publisher.*")
public class AdapterConfiguration {

}
