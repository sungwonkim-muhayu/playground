package org.github.swsz2.playground.missedmessage;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Entity
public class Content {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Setter
  @ManyToOne
  @JoinColumn(name = "board_id")
  @ToString.Exclude
  private Board board;
}
