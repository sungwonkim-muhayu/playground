package org.github.swsz2.playground.missedmessage;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public interface HttpStrategy {
  <T> ResponseEntity<?> fetch(
      final String uri, final HttpMethod method, final T payload, final Class<?> clazz);

  <K, V> ResponseEntity<?> fetch(
      final String uri,
      final HttpMethod method,
      final MultiValueMap<K, V> multiValueMap,
      final Class<?> clazz);

  ResponseEntity<?> fetch(final String uri, final HttpMethod method, final Class<?> clazz);

  Type getType();

  enum Type {
    SYNC_BLOCKING,
    SYNC_NONBLOCKING;
  }
}
