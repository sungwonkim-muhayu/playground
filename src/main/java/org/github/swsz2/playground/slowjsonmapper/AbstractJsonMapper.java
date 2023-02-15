package org.github.swsz2.playground.slowjsonmapper;

public abstract class AbstractJsonMapper<T> implements JsonMapper<T> {

  private final Class<T> type;

  protected AbstractJsonMapper(final Class<T> type) {
    this.type = type;
  }

  protected Class<T> getType() {
    return this.type;
  }
}
