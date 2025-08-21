package ru.practicum.exceptions;

public class DuplicateRequestException extends ConflictDataException {
    public DuplicateRequestException(String message) {
        super(message);
    }
}
