package ru.practicum.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.user.UserDto;
import ru.practicum.dto.user.UserRequestDto;
import ru.practicum.user.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(@RequestParam(required = false) List<Long> ids,
                                                  @RequestParam(defaultValue = "0") Integer from,
                                                  @RequestParam(defaultValue = "10") Integer size) {
        return ResponseEntity.ok().body(userService.getUsers(ids, from, size));
    }

    @PostMapping
    public ResponseEntity<UserDto> registerUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(userRequestDto));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
