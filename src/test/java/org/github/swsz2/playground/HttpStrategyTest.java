package org.github.swsz2.playground;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
@Import(RestTemplateConfiguration.class)
class HttpStrategyTest {

  @Autowired RestTemplate restTemplate;

  @Test
  void fetch() {}

  @Test
  void testFetch() {}
}
