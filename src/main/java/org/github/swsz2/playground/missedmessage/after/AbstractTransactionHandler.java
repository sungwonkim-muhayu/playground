package org.github.swsz2.playground.missedmessage.after;

/**
 * REQUIRED(0) : 부모 트랙잭션 내에서 실행, 부모 트랜잭션이 없을 경우 생성 (default) <br>
 * SUPPORTS(1) : 부모 트랜잭션 내에서 실행, 부모 트랜잭션이 없을 경우 트랜잭션 없이 실행 <br>
 * MANDATORY(2) : 부모 트랜잭션 내에서 실행, 부모 트랜잭션이 없을 경우 Exception 발생 <br>
 * REQUIRES_NEW(3) : 매번 새로운 트랜잭션 실행 <br>
 * NOT_SUPPORTED(4) : 트랜잭션 없이 실행하며, 부모 트랜잭션이 존재하면 정지 <br>
 * NEVER(5) : 트랜잭션 없이 실행하며, 부모 트랜잭션이 존재하면 Exception 발생 <br>
 * NESTED(6) : 부모 트랜잭션이 있을 경우 부모 트랜잭션과 별고래 커밋되거나 롤백될 수 있음, 트랜잭션이 없을 경우 REQUIRED와 동일하게 동작 <br>
 */
public abstract class AbstractTransactionHandler implements TransactionHandler {}
