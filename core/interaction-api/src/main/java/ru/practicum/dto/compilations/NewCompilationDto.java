package ru.practicum.dto.compilations;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewCompilationDto {
    @NotBlank(message = "Заголовок обязателен")
    @Size(min = 1, max = 50, message = "Заголовок должен быть от 1 до 50 символов")
    private String title;

    @Builder.Default
    private Boolean pinned = false;

    private List<@NotNull @Positive Long> eventsIds;
}
