package org.github.swsz2.playground.missedmessage.after;

import lombok.extern.slf4j.Slf4j;
import org.github.swsz2.playground.missedmessage.*;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AfterMissedMessageProcessor implements MissedMessageProcessor {
  private final ContentRepository contentRepository;
  private final MessagePublisher messagePublisher;

  public AfterMissedMessageProcessor(
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
