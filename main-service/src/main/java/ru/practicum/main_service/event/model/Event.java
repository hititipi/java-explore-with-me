package ru.practicum.main_service.event.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.practicum.main_service.category.model.Category;
import ru.practicum.main_service.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

import static ru.practicum.main_service.utils.Constants.*;

@Entity
@Table(name = "events", schema = "public")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = MAX_LENGTH_EVENT_TITLE)
    private String title;
    @Column(nullable = false, length = MAX_LENGTH_EVENT_DESCRIPTION)
    private String description;
    @Column(nullable = false, length = MAX_LENGTH_EVENT_ANNOTATION)
    private String annotation;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;
    @Column(nullable = false)
    private Boolean paid;
    @Column(nullable = false)
    private Integer participantLimit;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;
    @Column(nullable = false)
    private LocalDateTime createdOn;
    @Column(nullable = false)
    private LocalDateTime eventDate;
    private LocalDateTime publishedOn;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventState state;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User initiator;
    @Column(name = "confirmed_requests")
    private Long confirmedRequests;
    @Column(nullable = false)
    private Boolean requestModeration;

}
