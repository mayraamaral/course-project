package dev.mayra.courses.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class Mapper {
  private final ObjectMapper objectMapper;

  public <T, D> D convertToDTO(T entity, Class<D> dto) {
    return objectMapper.convertValue(entity, dto);
  }

  public <D, T> T convertToEntity(D dto, Class<T> entity) {
    return objectMapper.convertValue(dto, entity);
  }
}
