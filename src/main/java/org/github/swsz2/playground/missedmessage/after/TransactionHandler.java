package org.github.swsz2.playground.missedmessage.after;

public interface TransactionHandler {

  void process(final Task task);

  interface Task {
    void submit();
  }
}
