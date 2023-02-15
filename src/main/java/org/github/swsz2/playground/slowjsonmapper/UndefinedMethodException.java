package org.github.swsz2.playground.slowjsonmapper;

/** 구현하지 않은 메소드를 호출했을 경우 해당 예외가 발생한다. */
public class UndefinedMethodException extends RuntimeException {
  public UndefinedMethodException(final String message) {
    super(message);
  }
}
