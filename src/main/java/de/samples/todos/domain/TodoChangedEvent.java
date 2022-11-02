package de.samples.todos.domain;

import lombok.Getter;

public record TodoChangedEvent(
  @Getter
  Todo todo,
  @Getter
  ChangeType type
) {

  public enum ChangeType {
    CREATED, REPLACED, REMOVED
  }

}
