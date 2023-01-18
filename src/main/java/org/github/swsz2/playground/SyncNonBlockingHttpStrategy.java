package org.github.swsz2.playground;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;
import reactor.core.publisher.Mono;

import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class SyncNonBlockingHttpStrategy extends AbstractHttpStrategy {

  private final WebClient webClient;
  private final int responseCount;
  private final int timeoutSecond;

  private final Set<HttpMethod> httpMethodsWithPayload =
      Set.of(HttpMethod.POST, HttpMethod.PUT, HttpMethod.PATCH);

  public SyncNonBlockingHttpStrategy(final Builder webClientBuilder) {
    super(Type.SYNC_NONBLOCKING);
    this.webClient = webClientBuilder.build();
    this.responseCount = 1;
    this.timeoutSecond = 1;
  }

  @Override
  public <T> ResponseEntity<?> fetch(
      final String uri, final HttpMethod method, final T payload, final Class<?> clazz) {
    return fetch(uri, method, payload, MultiValueMaps.EMPTY, clazz);
  }

  @Override
  public <K, V> ResponseEntity<?> fetch(
      final String uri,
      final HttpMethod method,
      final MultiValueMap<K, V> multiValueMap,
      final Class<?> clazz) {
    return fetch(uri, method, null, multiValueMap, clazz);
  }

  @Override
  public ResponseEntity<?> fetch(final String uri, final HttpMethod method, final Class<?> clazz) {
    return fetch(uri, method, null, MultiValueMaps.EMPTY, clazz);
  }

  /**
   * Http Method에 따라 요청을 수행한다.
   *
   * @param uri uri
   * @param method method
   * @param payload payload
   * @param multiValueMap multiValueMap
   * @param clazz clazz
   * @return ResponseEntity<?>
   * @param <T> <T>
   * @param <K> <K>
   * @param <V> <V>
   */
  private <T, K, V> ResponseEntity<?> fetch(
      final String uri,
      final HttpMethod method,
      final T payload,
      final MultiValueMap<K, V> multiValueMap,
      final Class<?> clazz) {

    // 하나의 응답을 받은 것을 확인하기 위한 CountDownLatch 선언
    final CountDownLatch countDownLatch = new CountDownLatch(responseCount);

    // 값의 세팅을 확인하기 위한 Box 생성
    final Box box = new Box<>();

    Mono mono;

    if (httpMethodsWithPayload.contains(method)) {
      mono = createMonoWithPayload(uri, method, payload, clazz);
    } else {
      mono = createMonoWithMultiValueMap(uri, method, multiValueMap, clazz);
    }

    // 요청
    mono.subscribe(
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

  /**
   * Payload, Body 와 함께 요청하는 Http Method 일 경우 사용한다.
   *
   * @param uri uri
   * @param method method
   * @param payload payload
   * @param clazz clazz
   * @return Mono<?>
   * @param  <T> <T>
   */
  private <T> Mono<?> createMonoWithPayload(
      final String uri, final HttpMethod method, final T payload, final Class<?> clazz) {

    WebClient.RequestBodyUriSpec requestBodyUriSpec;

    switch (method) {
      case POST:
        requestBodyUriSpec = webClient.post();
        break;
      case PUT:
        requestBodyUriSpec = webClient.put();
        break;
      case PATCH:
        requestBodyUriSpec = webClient.patch();
        break;
      default:
        throw new UnsupportedHttpMethodException(method);
    }

    Assert.notNull(requestBodyUriSpec, "'requestHeadersSpec' is null");

    return requestBodyUriSpec
        .uri(uri)
        .bodyValue(payload)
        .retrieve()
        .onStatus(HttpStatus::isError, error -> Mono.error(RuntimeException::new))
        .bodyToMono(clazz);
  }

  /**
   * QueryString 과 함께 요청하는 Http Method 일 경우 사용한다.
   *
   * @param uri uri
   * @param method method
   * @param multiValueMap multiValueMap
   * @param clazz clazz
   * @return Mono<?>
   * @param <K> K
   * @param <V> V
   */
  private <K, V> Mono<?> createMonoWithMultiValueMap(
      final String uri,
      final HttpMethod method,
      final MultiValueMap<K, V> multiValueMap,
      final Class<?> clazz) {

    WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    switch (method) {
      case GET:
        requestHeadersUriSpec = webClient.get();
        break;
      case DELETE:
        requestHeadersUriSpec = webClient.delete();
        break;
      case HEAD:
        requestHeadersUriSpec = webClient.head();
        break;
      case OPTIONS:
        requestHeadersUriSpec = webClient.options();
        break;
      default:
        throw new UnsupportedHttpMethodException(method);
    }

    Assert.notNull(requestHeadersUriSpec, "'requestHeadersUriSpec' is null");

    return requestHeadersUriSpec
        .uri(uri, multiValueMap)
        .retrieve()
        .onStatus(HttpStatus::isError, error -> Mono.error(RuntimeException::new))
        .bodyToMono(clazz);
  }

  /**
   * 제한 시간 동안 countDownLatch 가 zero 에 도달하지 않으면 예외를 던진다.
   *
   * @param countDownLatch countDownLatch
   * @param timeout timeout
   * @param timeUnit timeUnit
   */
  @SneakyThrows
  private void throwWhenCountIsNotZeroOnTimeout(
      final CountDownLatch countDownLatch, final int timeout, final TimeUnit timeUnit) {
    if (!countDownLatch.await(timeout, timeUnit)) {
      throw new RuntimeException("Count is not zero on" + timeout + " " + timeUnit);
    }
  }
}
