package org.github.swsz2.playground;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public interface HttpStrategy {
  <T> ResponseEntity<?> fetch(
      final String uri, final HttpMethod method, final T payload, final Class<?> clazz);
}
