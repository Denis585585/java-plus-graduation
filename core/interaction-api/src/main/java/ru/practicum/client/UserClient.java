package ru.practicum.client;

import ru.practicum.dto.user.UserShortDto;

import java.util.List;

@FeignClient(name = "user-service", path = "/internal/api/users")
public interface UserClient {
    @GetMapping("/{userId}")
    UserShortDto getById(@PathVariable Long userId);

    @GetMapping
    List<UserShortDto> getByIds(@RequestParam(name = "id") List<Long> ids);
}
