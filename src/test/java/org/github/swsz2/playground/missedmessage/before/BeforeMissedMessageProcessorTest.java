package org.github.swsz2.playground.missedmessage.before;

import lombok.extern.slf4j.Slf4j;
import org.github.swsz2.playground.RestTemplateConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@Slf4j
@SpringBootTest(webEnvironment = DEFINED_PORT) // 실제 웹 서버 기동 후 진행하기 위함
@AutoConfigureWebMvc
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
