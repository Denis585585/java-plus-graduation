package ru.practicum.request.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.request.dto.EventFullDto;
import ru.practicum.request.client.EventClient;
import ru.practicum.request.client.UserClient;
import ru.practicum.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.request.dto.EventShortDto;
import ru.practicum.request.dto.RequestDto;
import ru.practicum.request.enums.EventState;
import ru.practicum.request.enums.RequestStatus;
import ru.practicum.request.exceptions.NotFoundException;
import ru.practicum.request.mapper.RequestMapper;
import ru.practicum.request.model.Request;
import ru.practicum.request.repository.RequestRepository;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final EventClient eventClient;
    private final UserClient userClient;

    @Override
    public List<RequestDto> getRequests(Integer userId) {
        userClient.findById(userId);
        return requestRepository.findAllByRequester_Id(userId).stream()
                .map(RequestMapper.INSTANCE::mapToRequestDto)
                .toList();
    }

    @Transactional
    @Override
    public RequestDto createRequest(Integer userId, Integer eventId) {
        EventFullDto event = eventClient.findById(eventId);
        checkRequest(userId, eventId);
        Request request = Request.builder()
                .requesterId(userId)
                .created(LocalDateTime.now())
                .status(!event.getRequestModeration()
                        || event.getParticipantLimit() == 0
                        ? RequestStatus.CONFIRMED : RequestStatus.PENDING)
                .eventId(eventId)
                .build();
        request = requestRepository.save(request);
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
        }
        return RequestMapper.INSTANCE.mapToRequestDto(request);
    }

    @Transactional
    @Override
    public RequestDto cancelRequest(Integer userId, Integer requestId) {
        Request request = requestRepository.findByRequesterAndId(userId, requestId)
                .orElseThrow(() -> new NotFoundException(String.format("Request with id=%d was not found", requestId)));
        request.setStatus(RequestStatus.CANCELED);
        return RequestMapper.INSTANCE.mapToRequestDto(request);
    }

    @Override
    public List<RequestDto> getRequestByUserOfEvent(Integer userId, Integer eventId) {
        List<EventShortDto> events = eventClient.getEventsByUser(userId, 0, 1000);
        return requestRepository.findAllByEventId(events.stream()
                        .map(EventShortDto::getId)
                        .filter(id -> Objects.equals(id, eventId)).toList())
                .stream()
                .map(RequestMapper.INSTANCE::mapToRequestDto)
                .toList();
    }

    @Transactional
    @Override
    public EventRequestStatusUpdateResult updateRequests(Integer userId, Integer eventId, EventRequestStatusUpdateRequest requestStatusUpdateRequest) {
        EventFullDto event = eventClient.findById(eventId);

        EventRequestStatusUpdateResult result = new EventRequestStatusUpdateResult();
        if (!event.getRequestModeration() || event.getParticipantLimit() == 0) {
            return result;
        }
        List<EventShortDto> events = eventClient.getEventsByUser(userId, 0, 1000);
        List<Request> requests = requestRepository.findAllByEventId(events.stream()
                .map(EventShortDto::getId)
                .filter(id -> Objects.equals(id, eventId))
                .toList());
        List<Request> requestsToUpdate = requests.stream()
                .filter(request -> requestStatusUpdateRequest.getRequestIds().contains(request.getId()))
                .toList();

        if (requestsToUpdate.stream().anyMatch(request -> request.getStatus().equals(RequestStatus.CONFIRMED)
                && requestStatusUpdateRequest.getStatus().equals(RequestStatus.REJECTED))) {
            throw new InvalidParameterException("Request already confirmed");
        }
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit().equals(event.getConfirmedRequests()))
            throw new InvalidParameterException("Exceeding the limit of participants");

        for (Request request : requestsToUpdate) {
            request.setStatus(RequestStatus.valueOf(requestStatusUpdateRequest.getStatus().toString()));
        }
        requestRepository.saveAll(requestsToUpdate);
        if (requestStatusUpdateRequest.getStatus().equals(RequestStatus.CONFIRMED)) {
            event.setConfirmedRequests(event.getConfirmedRequests() + requestsToUpdate.size());
        }
        if (requestStatusUpdateRequest.getStatus().equals(RequestStatus.CONFIRMED)) {
            event.setConfirmedRequests(event.getConfirmedRequests() + requestsToUpdate.size());
        }
        if (requestStatusUpdateRequest.getStatus().equals(RequestStatus.CONFIRMED)) {
            result.setConfirmedRequests(requestsToUpdate.stream()
                    .map(RequestMapper.INSTANCE::toParticipationRequestDto)
                    .toList());
        }
        if (requestStatusUpdateRequest.getStatus().equals(RequestStatus.REJECTED)) {
            result.setRejectedRequests(requestsToUpdate.stream()
                    .map(RequestMapper.INSTANCE::toParticipationRequestDto)
                    .toList());
        }
        return result;
    }

    private void checkRequest(Integer requesterId, Integer eventId) {
        if (requestRepository.existsByRequesterAndEvent(requesterId, eventId))
            throw new InvalidParameterException("Нельзя создать повторный запрос");
        EventFullDto event = eventClient.findById(eventId);
        if (event.getInitiatorId().equals(requesterId))
            throw new InvalidParameterException("Инициатор события не может добавить запрос на участие в своём событии");
        if (!event.getState().equals(EventState.PUBLISHED))
            throw new InvalidParameterException("Нельзя участвовать в неопубликованных событиях");
        if (event.getParticipantLimit() != 0 && event.getParticipantLimit().equals(event.getConfirmedRequests()))
            throw new InvalidParameterException("У события достигнут лимит запросов на участие");
        List<Request> requests = requestRepository.findAllByEvent_Id(eventId);
        if (!event.getRequestModeration() && requests.size() >= event.getParticipantLimit()) {
            throw new InvalidParameterException("Member limit exceeded ");
        }
    }

    private Request getRequest(Integer requestId) {
        Optional<Request> request = requestRepository.findById(requestId);
        if (request.isEmpty())
            throw new NotFoundException("Запроса с id " + requestId.toString() + " не существует");
        return request.get();
    }
}