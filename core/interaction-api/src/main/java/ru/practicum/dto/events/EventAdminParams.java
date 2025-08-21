package ru.practicum.dto.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventAdminParams {
    private List<Long> usersIds;
    private List<String> states;
    private List<Long> categoriesIds;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private int from;
    private int size;
}
