package org.github.swsz2.playground.missedmessage.after;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class RequiredNewTransactionHandler extends AbstractTransactionHandler {
  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void process(final Task task) {
    task.submit();
  }
}
