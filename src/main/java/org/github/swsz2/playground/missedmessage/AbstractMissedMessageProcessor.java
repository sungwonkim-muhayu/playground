package org.github.swsz2.playground.missedmessage;

public abstract class AbstractMissedMessageProcessor {
  protected final BoardRepository boardRepository;
  protected final ContentRepository contentRepository;
  protected final MessagePublisher messagePublisher;

  public AbstractMissedMessageProcessor(
      final BoardRepository boardRepository,
      final ContentRepository contentRepository,
      final MessagePublisher messagePublisher) {
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
}
