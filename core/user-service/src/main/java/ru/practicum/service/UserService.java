package ru.practicum.service;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.dto.UserDto;
import ru.practicum.param.UserParams;

import java.util.List;

@Transactional(readOnly = true)
public interface UserService {

    @Transactional
    UserDto addUser(UserDto userDto);

    @Transactional
    void deleteUser(Integer userId);

    List<UserDto> getUsers(UserParams userParams);
}
