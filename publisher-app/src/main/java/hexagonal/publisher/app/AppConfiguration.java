package hexagonal.publisher.app;

import hexagonal.publisher.adapter.AdapterConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(AdapterConfiguration.class)
public class AppConfiguration {

}
