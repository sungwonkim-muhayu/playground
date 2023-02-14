package org.github.swsz2.playground.missedmessage;

/** HttpStrategy.Type에 해당하는 전략을 찾지 못했을 경우 해당 예외가 발생한다. */
public class HttpStrategyNotFoundException extends RuntimeException {
  public HttpStrategyNotFoundException() {
    super();
  }
}
