package org.github.swsz2.playground.slowjsonmapper;

import lombok.Getter;

@Getter
public class User {
  private final String id;
  private final String name;

  public User(final String id, final String name) {
    this.id = id;
    this.name = name;
  }
}
