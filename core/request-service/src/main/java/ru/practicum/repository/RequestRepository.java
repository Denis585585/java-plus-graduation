package ru.practicum.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.model.Request;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    List<Request> findAllByEvent_Id(Integer eventId);

    List<Request> findAllByRequester_Id(Integer userId);

    List<Request> findAllByEventId(List<Integer> eventIds);

    Boolean existsByRequesterAndEvent(Integer requesterId, Integer eventId);

    Optional<Request> findByRequesterAndId(Integer userId, Integer requestId);

}
