package ru.practicum.user.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.user.UserDto;
import ru.practicum.dto.user.UserRequestDto;
import ru.practicum.dto.user.UserShortDto;
import ru.practicum.user.param.UserParams;

import java.util.List;

@Transactional(readOnly = true)
public interface UserService {

    @Transactional
    UserDto addUser(UserDto userDto);

    @Transactional
    void deleteUser(Long userId);

    List<UserDto> getUsers(UserParams userParams);

    List<UserShortDto> getUsers(List<Long> ids);

    List<UserDto> getUsers(List<Long> ids, Integer from, Integer size);

    UserShortDto getById(Long userId);

    UserDto registerUser(UserRequestDto userRequestDto);
}
