package ru.practicum.events.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.UserDto;
import ru.practicum.events.dto.EventFullDto;
import ru.practicum.events.dto.EventPublicParam;
import ru.practicum.events.dto.EventShortDto;
import ru.practicum.events.service.EventService;
import ru.practicum.events.util.SortState;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventPublicController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventShortDto>> publicGetEvents(@RequestParam(required = false) String text,
                                                               @RequestParam(required = false) List<Integer> categories,
                                                               @RequestParam(required = false) Boolean paid,
                                                               @RequestParam(required = false)
                                                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                                               @RequestParam(required = false)
                                                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                                               @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                                               @RequestParam(required = false) SortState sort,
                                                               @RequestParam(defaultValue = "0") Integer from,
                                                               @RequestParam(defaultValue = "10") Integer size,
                                                               HttpServletRequest request) {
        EventPublicParam eventPublicParam = new EventPublicParam(text, categories, paid,
                rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        eventService.saveHit(request);
        return ResponseEntity.ok().body(eventService.publicGetEvents(eventPublicParam));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventFullDto> publicGetEvent(@PathVariable Integer eventId, HttpServletRequest request) {
        eventService.saveHit(request);
        return ResponseEntity.ok().body(eventService.publicGetEvent(eventId));
    }

    @GetMapping("/{eventId}/likes")
    public ResponseEntity<List<UserDto>> publicGetLikedUsers(@PathVariable("eventId") Integer eventId, HttpServletRequest request) {
        eventService.saveHit(request);
        return ResponseEntity.ok(eventService.getLikedUsers(eventId));
    }


}