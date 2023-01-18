package org.github.swsz2.playground.missedmessage.before;

import lombok.extern.slf4j.Slf4j;
import org.github.swsz2.playground.missedmessage.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 데이터베이스에 데이터를 저장한 후 이벤트 브로커에 메시지를 전달해야 했다. <br>
 * 그러나 이벤트 브로커에 메시지를 전달한 후 데이터베이스에 데이터를 저장하는 이슈가 발생했다. <br>
 * 이 이슈는 클라이언트 애플리케이션의 조회 시점에 따라 장애 상황으로 이어질 가능성이 있었다. <br>
 */
@Slf4j
@Service
public class BeforeMissedMessageProcessor implements MissedMessageProcessor {

  private final BoardRepository boardRepository;
  private final ContentRepository contentRepository;
  private final MessagePublisher messagePublisher;

  public BeforeMissedMessageProcessor(
      final BoardRepository boardRepository,
      final ContentRepository contentRepository,
      final SyncBlockingMessagePublisher messagePublisher) {
    this.boardRepository = boardRepository;
    this.contentRepository = contentRepository;
    this.messagePublisher = messagePublisher;
  }

  public void prepareEntities() {
    final Board board = new Board();
    for (int i = 0; i < 5; i++) {
      final Content content = new Content();
      board.addContent(content);
    }
    boardRepository.save(board);
  }

  @Override
  @Transactional
  public void doProcess() {
    log.info("enter doProcess");
    final Board board = boardRepository.findAll().get(0);
    save(board);
    publish();
  }

  private void save(final Board board) {
    log.info("enter save()");
    board.addContent(new Content());
  }

  private void publish() {
    log.info("enter publish()");
    messagePublisher.publish();
  }
}
