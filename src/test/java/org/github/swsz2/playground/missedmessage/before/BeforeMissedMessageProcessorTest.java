package org.github.swsz2.playground.missedmessage.before;

import lombok.extern.slf4j.Slf4j;
import org.github.swsz2.playground.RestTemplateConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
@Import(RestTemplateConfiguration.class)
class BeforeMissedMessageProcessorTest {

  @Autowired BeforeMissedMessageProcessor beforeMissedMessageProcessor;

  @BeforeEach
  void setUp() {
    beforeMissedMessageProcessor.prepareEntities();
  }

  @Test
  void doProcess() {
      beforeMissedMessageProcessor.doProcess();
  }
}
