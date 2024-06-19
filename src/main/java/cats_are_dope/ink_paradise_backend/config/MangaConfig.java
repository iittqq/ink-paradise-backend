package cats_are_dope.ink_paradise_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MangaConfig {
  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
