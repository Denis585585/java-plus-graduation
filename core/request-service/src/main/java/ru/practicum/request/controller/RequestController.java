package ru.practicum.request.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.service.RequestService;

import java.util.List;

@RestController
@Validated
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users/{userId}/requests")
public class RequestController {
    private final RequestService requestService;

    @GetMapping
    public List<RequestDto> getRequests(@PathVariable Integer userId) {
        log.info("Получить запросы по userId --> {}", userId);
        return requestService.getRequests(userId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public RequestDto createRequest(@PathVariable Integer userId,
                                    @RequestParam Integer eventId) {
        log.info("Создать запрос userId --> {}, eventId --> {}", userId, eventId);
        return requestService.createRequest(userId, eventId);
    }

    @PatchMapping("/{requestId}/cancel")
    public RequestDto cancelRequest(@PathVariable Integer userId,
                                    @PathVariable Integer requestId) {
        log.info("Отменить запрос по userId --> {}, requestId --> {}", userId, requestId);
        return requestService.cancelRequest(userId, requestId);
    }

    @GetMapping("/{eventId}/requests")
    public ResponseEntity<List<RequestDto>> getRequestsByUserOfEvent(@PathVariable Integer userId,
                                                                     @PathVariable Integer eventId) {
        return ResponseEntity.ok().body(requestService.getRequestByUserOfEvent(userId, eventId));
    }

    @PatchMapping("/{eventId}/requests")
    public ResponseEntity<EventRequestStatusUpdateResult> updateRequests(@PathVariable Integer userId,
                                                                         @PathVariable Integer eventId,
                                                                         @RequestBody EventRequestStatusUpdateRequest updateRequest) {
        return ResponseEntity.ok().body(requestService.updateRequests(userId, eventId, updateRequest));
    }
}
