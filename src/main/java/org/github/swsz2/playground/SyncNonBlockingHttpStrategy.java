package org.github.swsz2.playground;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import reactor.core.publisher.Mono;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class SyncNonBlockingHttpStrategy extends AbstractHttpStrategy {

  private final WebClient webClient;
  private final int responseCount = 1;
  private final int timeoutSecond = 1;

  public SyncNonBlockingHttpStrategy(final Builder webClientBuilder) {
    this.webClient = webClientBuilder.build();
  }

  @Override
  public <T> ResponseEntity<?> fetch(
      final String uri, final HttpMethod method, final T payload, final Class<?> clazz) {

    // 하나의 응답을 받은 것을 확인하기 위한 CountDownLatch 선언
    final CountDownLatch countDownLatch = new CountDownLatch(responseCount);

    // 값의 세팅을 확인하기 위한 Box 생성
    final Box box = new Box<>();

    // 요청
    createMono(uri, method, payload, clazz)
        .subscribe(
            body -> {
              box.setTarget(body);
              countDownLatch.countDown();
            });

    // 일정 시간 내에 응답을 전달하지 못 할 경우 예외 발생
    //// 실제 데이터 세팅까지 완료된 시간으로 제한을 두기 위해서 webclient의 timeout을 사용하지 않음
    throwWhenCountIsNotZeroOnTimeout(countDownLatch, timeoutSecond, TimeUnit.SECONDS);

    // 값이 세팅됐을 경우 target을 포함한 ResponseEntity를 반환하며 그렇지 않을 경우 500 error를 포함한 ResponseEntity를 반환한다.
    return box.isFixed()
        ? ResponseEntity.ok(box.getTarget())
        : ResponseEntity.internalServerError().build();
  }

  private <T> Mono<?> createMono(
      final String uri, final HttpMethod method, final T payload, final Class<?> clazz) {
    switch (method) {
      case GET:
        return webClient
            .get()
            .uri(uri)
            .retrieve()
            .onStatus(HttpStatus::isError, error -> Mono.error(RuntimeException::new))
            .bodyToMono(clazz);
      case POST:
        return webClient
            .post()
            .uri(uri)
            .bodyValue(payload)
            .retrieve()
            .onStatus(HttpStatus::isError, error -> Mono.error(RuntimeException::new))
            .bodyToMono(clazz);
      case PUT:
        return webClient
            .put()
            .uri(uri)
            .bodyValue(payload)
            .retrieve()
            .onStatus(HttpStatus::isError, error -> Mono.error(RuntimeException::new))
            .bodyToMono(clazz);
      case PATCH:
        return webClient
            .patch()
            .uri(uri)
            .bodyValue(payload)
            .retrieve()
            .onStatus(HttpStatus::isError, error -> Mono.error(RuntimeException::new))
            .bodyToMono(clazz);
      case DELETE:
        return webClient
            .delete()
            .uri(uri)
            .retrieve()
            .onStatus(HttpStatus::isError, error -> Mono.error(RuntimeException::new))
            .bodyToMono(clazz);
      case HEAD:
        return webClient
            .head()
            .uri(uri)
            .retrieve()
            .onStatus(HttpStatus::isError, error -> Mono.error(RuntimeException::new))
            .bodyToMono(clazz);
      case OPTIONS:
        return webClient
            .options()
            .uri(uri)
            .retrieve()
            .onStatus(HttpStatus::isError, error -> Mono.error(RuntimeException::new))
            .bodyToMono(clazz);
      default:
        throw new UnsupportedHttpMethodException(method);
    }
  }

  @SneakyThrows
  private void throwWhenCountIsNotZeroOnTimeout(
      final CountDownLatch countDownLatch, final int timeout, final TimeUnit timeUnit) {
    if (!countDownLatch.await(timeout, timeUnit)) {
      throw new RuntimeException("Count is not zero on" + timeout + " " + timeUnit);
    }
  }
}
