package org.github.swsz2.playground.slowjsonmapper;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface JsonMapper<T> {
  default T toEntity(final String value) throws JsonProcessingException {
    throw new UndefinedMethodException("JsonMapper.toEntity(value)");
  }
}
