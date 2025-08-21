package ru.practicum.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.dto.user.UserShortDto;
import org.springframework.cloud.openfeign.FeignClient;


import java.util.List;

@FeignClient(name = "user-service", path = "/internal/api/users")
public interface UserClient {
    @GetMapping("/{userId}")
    UserShortDto getById(@PathVariable Long userId);

    @GetMapping
    List<UserShortDto> getByIds(@RequestParam(name = "id") List<Long> ids);
}
