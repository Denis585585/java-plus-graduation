package ru.practicum.dto.compilations;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.practicum.dto.events.EventShortDto;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompilationDto {
    @NotNull(message = "ID подборки обязателен")
    private Long id;

    @NotNull(message = "Флаг закрепления обязателен")
    private Boolean pinned;

    @NotNull(message = "Заголовок обязателен")
    private String title;

    private List<@NotNull EventShortDto> events;
}
