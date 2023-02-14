package org.github.swsz2.playground.missedmessage;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class SyncBlockingMessagePublisher implements MessagePublisher {

  private final HttpStrategy httpStrategy;

  public SyncBlockingMessagePublisher(final HttpStrategyFactory httpStrategyFactory) {
    this.httpStrategy = httpStrategyFactory.findByType(HttpStrategy.Type.SYNC_BLOCKING);
  }

  @Override
  public void publish() {
    httpStrategy.fetch("http://localhost:8080/mm/publish", HttpMethod.GET, Boolean.class);
  }
}
