package org.github.swsz2.playground.missedmessage;

public abstract class AbstractHttpStrategy implements HttpStrategy {
  final Type type;

  AbstractHttpStrategy(final Type type) {
    this.type = type;
  }

  @Override
  public Type getType() {
    return this.type;
  }
}
