package org.github.swsz2.playground.missedmessage;

import lombok.Getter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Getter
@Entity
public class Board {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id", nullable = false)
  private Long id;

  @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
  private final List<Content> contents = new LinkedList<>();

  public void addContent(final Content content) {
    content.setBoard(this);
    this.contents.add(content);
  }
}
