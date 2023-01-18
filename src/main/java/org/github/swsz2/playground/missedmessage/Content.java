package org.github.swsz2.playground.missedmessage;

import javax.persistence.*;

@Entity
public class Content {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;
}
