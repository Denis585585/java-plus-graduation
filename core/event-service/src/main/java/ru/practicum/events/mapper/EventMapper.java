package ru.practicum.events.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.practicum.categories.mapper.CategoryMapper;
import ru.practicum.categories.model.Category;
import ru.practicum.dto.events.EventFullDto;
import ru.practicum.dto.events.EventShortDto;
import ru.practicum.dto.events.EventState;
import ru.practicum.dto.events.NewEventDto;
import ru.practicum.dto.user.UserShortDto;
import ru.practicum.events.model.Event;
import ru.practicum.events.model.Location;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class, LocationMapper.class}, imports = {LocalDateTime.class, EventState.class})
public interface EventMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "confirmedRequests", expression = "java(0L)")
    @Mapping(target = "views", expression = "java(0L)")
    @Mapping(target = "createdOn", expression = "java(LocalDateTime.now())")
    @Mapping(target = "publishedOn", ignore = true)
    @Mapping(target = "state", expression = "java(EventState.PENDING)")
    @Mapping(target = "initiatorId", source = "initiatorId")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "location", source = "newEventDto.location")
    Event toEvent(NewEventDto newEventDto, Category category, Long initiatorId);

    @Mapping(target = "id", source = "event.id")
    @Mapping(target = "confirmedRequests", source = "event.confirmedRequests", defaultExpression = "java(0L)")
    @Mapping(target = "views", source = "event.views", defaultExpression = "java(0L)")
    @Mapping(target = "initiator", ignore = true)
    EventFullDto toEventFullDto(Event event);

    @Mapping(target = "id", source = "event.id")
    @Mapping(target = "confirmedRequests", source = "event.confirmedRequests", defaultExpression = "java(0L)")
    @Mapping(target = "views", source = "event.views", defaultExpression = "java(0L)")
    @Mapping(target = "initiator", source = "userShortDto")
    EventFullDto toEventFullDto(Event event, UserShortDto userShortDto);

    @Mapping(target = "id", source = "event.id")
    @Mapping(target = "confirmedRequests", source = "event.confirmedRequests", defaultExpression = "java(0L)")
    @Mapping(target = "views", source = "event.views", defaultExpression = "java(0L)")
    @Mapping(target = "initiator", source = "initiator")
    EventShortDto toEventShortDto(Event event, UserShortDto initiator);

    List<EventFullDto> toEventFullDto(List<Event> events);

    @AfterMapping
    default void setLocation(@MappingTarget Event event, NewEventDto newEventDto) {
        if (newEventDto.getLocation() != null) {
            Location location = new Location();
            location.setLat(newEventDto.getLocation().getLat());
            location.setLon(newEventDto.getLocation().getLon());
            event.setLocation(location);
        }
    }

    @AfterMapping
    default void setCategory(@MappingTarget Event event, Category category) {
        event.setCategory(category);
    }
}