package org.github.swsz2.playground.missedmessage.before;

import lombok.extern.slf4j.Slf4j;
import org.github.swsz2.playground.missedmessage.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class BeforeMissedMessageProcessor implements MissedMessageProcessor {

  private final ContentRepository contentRepository;
  private final MessagePublisher messagePublisher;

  public BeforeMissedMessageProcessor(
      final ContentRepository contentRepository,
      final SyncBlockingMessagePublisher messagePublisher) {
    this.contentRepository = contentRepository;
    this.messagePublisher = messagePublisher;
  }


  @Override
  @Transactional
  @EventListener(ApplicationReadyEvent.class)
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
