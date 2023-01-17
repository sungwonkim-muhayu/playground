package org.github.swsz2.playground;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SyncBlockingHttpStrategy extends AbstractHttpStrategy {

  private final RestTemplate restTemplate;

  public SyncBlockingHttpStrategy(final RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public <T> ResponseEntity<?> fetch(
      final String uri, final HttpMethod method, final T payload, final Class<?> clazz) {
    return restTemplate.exchange(uri, method, new HttpEntity<>(payload), clazz);
  }
}
