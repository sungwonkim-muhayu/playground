package org.github.swsz2.playground.missedmessage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "org.github.swsz2.playground")
@SpringBootApplication
public class MissedMessageApplication {

  public static void main(String[] args) {
    SpringApplication.run(MissedMessageApplication.class, args);
  }
}
