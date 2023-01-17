package org.github.swsz2.playground;

import org.springframework.http.HttpMethod;

/** 지원하지 않는 HttpMethod일 때 해당 예외가 발생한다. */
public class UnsupportedHttpMethodException extends RuntimeException {
  public UnsupportedHttpMethodException(final HttpMethod httpMethod) {
    super("unsupported http method : " + httpMethod.name());
  }
}
