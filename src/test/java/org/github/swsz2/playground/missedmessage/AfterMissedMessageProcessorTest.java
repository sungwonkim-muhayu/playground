package org.github.swsz2.playground.missedmessage;

import lombok.extern.slf4j.Slf4j;
import org.github.swsz2.playground.missedmessage.after.AfterMissedMessageProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

@Slf4j
@SpringBootTest(
    webEnvironment = DEFINED_PORT,
    classes = {MissedMessageApplication.class}) // 실제 웹 서버 기동 후 진행하기 위함
@AutoConfigureWebMvc
@Import(RestTemplateConfiguration.class)
class AfterMissedMessageProcessorTest {

  @Autowired AfterMissedMessageProcessor afterMissedMessageProcessor;

  @BeforeEach
  void setUp() {
    afterMissedMessageProcessor.prepareEntities();
  }

  @Test
  void doProcess() {
    afterMissedMessageProcessor.doProcess();
  }
}
