package ru.practicum.exceptions;

public class ParticipantLimitReachedException extends ConflictDataException {
  public ParticipantLimitReachedException(String message) {
    super(message);
  }
}
