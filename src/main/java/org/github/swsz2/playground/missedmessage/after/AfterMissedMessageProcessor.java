package org.github.swsz2.playground.missedmessage.after;

import lombok.extern.slf4j.Slf4j;
import org.github.swsz2.playground.missedmessage.*;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AfterMissedMessageProcessor extends AbstractMissedMessageProcessor
    implements MissedMessageProcessor {

  private final TransactionHandler transactionHandler;

  public AfterMissedMessageProcessor(
      final BoardRepository boardRepository,
      final ContentRepository contentRepository,
      final SyncBlockingMessagePublisher messagePublisher,
      final RequiredNewTransactionHandler transactionHandler) {
    super(boardRepository, contentRepository, messagePublisher);
    this.transactionHandler = transactionHandler;
  }

  @Override
  public void doProcess() {
    System.out.println("enter doProcess");
    transactionHandler.process(
        () -> {
          final Board board = boardRepository.findAll().get(0);
          save(board);
        });
    publish();
  }

  private void save(final Board board) {
    System.out.println("enter save()");
    board.addContent(new Content());
  }

  private void publish() {
    System.out.println("enter publish()");
    messagePublisher.publish();
  }
}
