package org.github.swsz2.playground.missedmessage.after;

/** 익명 함수 또는 Task를 입력 받아 트랜잭션 관련 처리를 수행하는 인터페이스. */
public interface TransactionHandler {

  void execute(final Task task);

  interface Task {
    void submit();
  }
}
