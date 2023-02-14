package org.github.swsz2.playground.missedmessage;

import lombok.Getter;

@Getter
public class Box<T> {

  private T target;
  private boolean fixed;

  public Box() {
    this.fixed = false;
  }

  public synchronized void setTarget(final T target) {
    // 이미 설정된 값이 있을 경우 변경되지 않음
    if (fixed) {
      return;
    }
    // parameter target 이 존재할 경우에만 target, fixed 를 설정함
    if (target != null) {
      this.target = target;
      this.fixed = true;
    }
  }
}
