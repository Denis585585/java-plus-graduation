package ru.practicum.request.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RequestCount {
    private Long eventId;
    private Long quantity;
}
