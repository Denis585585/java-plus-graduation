package ru.practicum.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestCountDto {
    private Long eventId;
    private Long quantity;
}
