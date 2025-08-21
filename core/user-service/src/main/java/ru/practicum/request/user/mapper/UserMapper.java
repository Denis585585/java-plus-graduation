package ru.practicum.request.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import ru.practicum.request.dto.user.UserDto;
import ru.practicum.request.dto.user.UserRequestDto;
import ru.practicum.request.dto.user.UserShortDto;
import ru.practicum.request.user.model.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    User toEntity(UserRequestDto dto);

    UserDto toUserDto(User user);

    User toUser(UserDto userDto);

    UserShortDto toUserShortDto(User user);
}
