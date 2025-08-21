package ru.practicum.exceptions;

public class EventNotPublishedException extends ConflictDataException {
    public EventNotPublishedException(String message) {
        super(message);
    }
}
