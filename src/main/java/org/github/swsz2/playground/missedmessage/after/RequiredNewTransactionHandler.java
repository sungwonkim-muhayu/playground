package org.github.swsz2.playground.missedmessage.after;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/** 부모 트랜잭션이 존재하더라도 새로운 트랜잭션을 생성해 익명 함수 또는 Task를 처리하는 트랜잭션 핸들러. */
@Component
public class RequiredNewTransactionHandler extends AbstractTransactionHandler {
  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void execute(final Task task) {
    task.submit();
  }
}
