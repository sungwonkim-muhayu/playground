package org.github.swsz2.playground.slowjsonmapper.after;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.github.swsz2.playground.slowjsonmapper.AbstractJsonMapper;
import org.github.swsz2.playground.slowjsonmapper.User;

public class UserJacksonJsonMapper extends AbstractJsonMapper<User> {

  private final ObjectMapper objectMapper;

  public UserJacksonJsonMapper() {
    super(User.class);
    this.objectMapper = new ObjectMapper();
  }

  @Override
  public User toEntity(final String value) throws JsonProcessingException {
    return objectMapper.readValue(value, getType());
  }
}
