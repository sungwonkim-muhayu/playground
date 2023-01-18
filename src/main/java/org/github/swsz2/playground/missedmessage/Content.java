package org.github.swsz2.playground.missedmessage;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
public class Content {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  @Setter
  @ManyToOne
  @JoinColumn(name = "board_id")
  private Board board;
}
