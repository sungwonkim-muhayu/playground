package org.github.swsz2.playground.missedmessage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MissedMessageController {

  @GetMapping(value = "/mm/publish")
  public ResponseEntity<Boolean> publish() {
    System.out.println("Publish Message!");
    return ResponseEntity.ok(Boolean.TRUE);
  }
}
