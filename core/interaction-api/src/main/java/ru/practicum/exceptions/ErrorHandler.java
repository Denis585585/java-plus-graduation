package ru.practicum.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.InvalidParameterException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(final NotFoundException e) {
        log.error("{} {}", HttpStatus.NOT_FOUND, e.getMessage(), e);
        return new ApiError(
                HttpStatus.NOT_FOUND,
                "The required object was not found.",
                e.getMessage(),
                getStackTrace(e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMethodArgumentNotValid(final MethodArgumentNotValidException e) {
        log.error("{} {}", HttpStatus.BAD_REQUEST, e.getMessage(), e);
        return new ApiError(
                HttpStatus.BAD_REQUEST,
                "Incorrectly made request.",
                e.getMessage(),
                getStackTrace(e));
    }

    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleInvalidDataException(final InvalidDataException e) {
        log.error("{} {}", HttpStatus.BAD_REQUEST, e.getMessage(), e);
        return new ApiError(
                HttpStatus.BAD_REQUEST,
                "Incorrectly made request.",
                e.getMessage(),
                getStackTrace(e));
    }

    @ExceptionHandler(EventDateValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleEventDateValidationException(final EventDateValidationException e) {
        log.error("{} {}", HttpStatus.BAD_REQUEST, e.getMessage(), e);
        return new ApiError(
                HttpStatus.BAD_REQUEST,
                "Incorrectly made request.",
                e.getMessage(),
                getStackTrace(e));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleMissingServletRequestParameterException(final MissingServletRequestParameterException e) {
        log.error("{} {}", HttpStatus.BAD_REQUEST, e.getMessage(), e);
        return new ApiError(
                HttpStatus.BAD_REQUEST,
                "Incorrectly made request.",
                e.getMessage(),
                getStackTrace(e));
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidationException(ValidationException e) {
        log.error("Validation error: {}", e.getMessage(), e);
        return new ApiError(
                HttpStatus.BAD_REQUEST,
                "Incorrectly made request",
                e.getMessage(),
                getStackTrace(e)
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleDataIntegrityViolationException(final DataIntegrityViolationException e) {
        log.error("{} {}", HttpStatus.CONFLICT, e.getMessage(), e);
        return new ApiError(
                HttpStatus.CONFLICT,
                "Integrity constraint has been violated.",
                e.getMessage(),
                getStackTrace(e));
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleIllegalStateException(final IllegalStateException e) {
        log.error("{} {}", HttpStatus.CONFLICT, e.getMessage(), e);
        return new ApiError(
                HttpStatus.CONFLICT,
                "Integrity constraint has been violated.",
                e.getMessage(),
                getStackTrace(e));
    }

    @ExceptionHandler(ConflictDataException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConflictDataException(final ConflictDataException e) {
        log.error("{} {}", HttpStatus.CONFLICT, e.getMessage(), e);
        return new ApiError(
                HttpStatus.CONFLICT,
                "Integrity constraint has been violated.",
                e.getMessage(),
                getStackTrace(e));
    }

    @ExceptionHandler(DuplicateEmailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleDuplicateEmailException(DuplicateEmailException e) {
        log.error("Duplicate email: {}", e.getMessage(), e);
        return new ApiError(
                HttpStatus.CONFLICT,
                "Duplicate email",
                e.getMessage(),
                getStackTrace(e)
        );
    }

    @ExceptionHandler(InitiatorParticipationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleInitiatorParticipationException(InitiatorParticipationException e) {
        log.error("Initiator participation: {}", e.getMessage(), e);
        return new ApiError(
                HttpStatus.CONFLICT,
                "Initiator cannot participate",
                e.getMessage(),
                getStackTrace(e)
        );
    }

    @ExceptionHandler(DuplicateRequestException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleDuplicateRequestException(DuplicateRequestException e) {
        log.error("Duplicate request: {}", e.getMessage(), e);
        return new ApiError(
                HttpStatus.CONFLICT,
                "Duplicate participation request",
                e.getMessage(),
                getStackTrace(e)
        );
    }

    @ExceptionHandler(EventNotPublishedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleEventNotPublishedException(EventNotPublishedException e) {
        log.error("Event not published: {}", e.getMessage(), e);
        return new ApiError(
                HttpStatus.CONFLICT,
                "Event not published",
                e.getMessage(),
                getStackTrace(e)
        );
    }

    @ExceptionHandler(ParticipantLimitReachedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleParticipantLimitReachedException(ParticipantLimitReachedException e) {
        log.error("Participant limit: {}", e.getMessage(), e);
        return new ApiError(
                HttpStatus.CONFLICT,
                "Participant limit reached",
                e.getMessage(),
                getStackTrace(e)
        );
    }

    @ExceptionHandler(InvalidParameterException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleInvalidParameterException(final InvalidParameterException e) {
        log.error("{} {}", HttpStatus.CONFLICT, e.getMessage(), e);
        return new ApiError(
                HttpStatus.CONFLICT,
                "Integrity constraint has been violated.",
                e.getMessage(),
                getStackTrace(e));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleHttpMessageNotReadableException(final HttpMessageNotReadableException e) {
        log.error("{} {}", HttpStatus.BAD_REQUEST, e.getMessage(), e);
        return new ApiError(
                HttpStatus.BAD_REQUEST,
                "Incorrectly made request.",
                e.getMessage(),
                getStackTrace(e));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleHandlerMethodValidationException(final HandlerMethodValidationException e) {
        log.error("{} {}", HttpStatus.BAD_REQUEST, e.getMessage(), e);
        return new ApiError(
                HttpStatus.BAD_REQUEST,
                "Incorrectly made request.",
                e.getMessage(),
                getStackTrace(e));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleException(final Exception e) {
        log.error("500 {}", e.getMessage(), e);
        return new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error occurred",
                e.getMessage(),
                getStackTrace(e));
    }

    private String getStackTrace(final Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
