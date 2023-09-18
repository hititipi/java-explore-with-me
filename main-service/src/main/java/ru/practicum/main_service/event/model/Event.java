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
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = MAX_LENGTH_EVENT_TITLE)
    private String title;
    @Column(nullable = false, length = MAX_LENGTH_EVENT_ANNOTATION)
    private String annotation;
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;
    @Column(nullable = false, length = MAX_LENGTH_EVENT_DESCRIPTION)
    private String description;
    @Column(nullable = false)
    private Boolean paid = false;
    @Column(nullable = false)
    private Integer participantLimit;
    @Column(nullable = false)
    private LocalDateTime eventDate;
    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;
    @Column(nullable = false)
    private LocalDateTime createdOn;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventState state;
    private LocalDateTime publishedOn;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User initiator;
    @Column(nullable = false)
    private Boolean requestModeration = true;

}
