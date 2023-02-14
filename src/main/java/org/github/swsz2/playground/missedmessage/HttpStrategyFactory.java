package org.github.swsz2.playground.missedmessage;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class HttpStrategyFactory {
  private final Set<HttpStrategy> httpStrategies;

  public HttpStrategyFactory(final Set<HttpStrategy> httpStrategies) {
    this.httpStrategies = httpStrategies;
  }

  public HttpStrategy findByType(final HttpStrategy.Type type) {
    return httpStrategies.stream()
        .filter(httpStrategy -> type.equals(httpStrategy.getType()))
        .findFirst()
        .orElseThrow(HttpStrategyNotFoundException::new);
  }
}
