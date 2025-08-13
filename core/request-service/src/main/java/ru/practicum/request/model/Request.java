package ru.practicum.request.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import ru.practicum.request.enums.RequestStatus;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "requests")
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime created;

    @Column(name = "event_id")
    private Integer eventId;

    @Column(name = "requester_id")
    private Integer requesterId;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;
}