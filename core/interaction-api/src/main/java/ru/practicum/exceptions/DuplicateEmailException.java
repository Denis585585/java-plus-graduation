package ru.practicum.exceptions;

public class DuplicateEmailException extends ConflictDataException {
    public DuplicateEmailException(String message) {
        super(message);
    }
}
