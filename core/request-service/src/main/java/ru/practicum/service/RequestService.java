package ru.practicum.service;


import ru.practicum.dto.EventRequestStatusUpdateRequest;
import ru.practicum.dto.EventRequestStatusUpdateResult;
import ru.practicum.dto.RequestDto;

import java.util.List;

public interface RequestService {
    List<RequestDto> getRequests(Integer userId);

    RequestDto createRequest(Integer userId, Integer eventId);

    RequestDto cancelRequest(Integer userId, Integer requestId);

    List<RequestDto> getRequestByUserOfEvent(Integer userId, Integer eventId);

    EventRequestStatusUpdateResult updateRequests(Integer userId, Integer eventId, EventRequestStatusUpdateRequest requestStatusUpdateRequest);
}
