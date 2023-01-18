package org.github.swsz2.playground.missedmessage.after;

import lombok.extern.slf4j.Slf4j;
import org.github.swsz2.playground.missedmessage.*;
import org.springframework.stereotype.Service;

/**
 * 데이터베이스에 데이터를 저장한 후 이벤트 브로커에 메시지를 전달해야 했다. <br>
 * 그러나 이벤트 브로커에 메시지를 전달한 후 데이터베이스에 데이터를 저장하는 이슈가 발생했다. <br>
 * 이 이슈는 클라이언트 애플리케이션의 조회 시점에 따라 장애 상황으로 이어질 가능성이 있었다. <br>
 */
@Slf4j
@Service
public class AfterProcessor implements MissedMessageProcessor {
  private final ContentRepository contentRepository;
  private final MessagePublisher messagePublisher;

  public AfterProcessor(
      final ContentRepository contentRepository,
      final SyncBlockingMessagePublisher messagePublisher) {
    this.contentRepository = contentRepository;
    this.messagePublisher = messagePublisher;
  }

  @Override
  public void doProcess() {
    log.info("enter doProcess");

    final Content content = new Content();

    save(content);
    publish();
  }

  public void save(final Content content) {
    log.info("enter save()");
    contentRepository.save(content);
  }

  public void publish() {
    log.info("enter publish()");
    messagePublisher.publish();
  }
}
