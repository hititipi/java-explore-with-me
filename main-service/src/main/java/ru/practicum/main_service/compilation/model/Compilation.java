package ru.practicum.main_service.compilation.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.practicum.main_service.event.model.Event;

import javax.persistence.*;
import java.util.List;

import static ru.practicum.main_service.utils.Constants.MAX_LENGTH_EVENT_TITLE;

@Entity
@Table(name = "compilations", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Compilation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = MAX_LENGTH_EVENT_TITLE, unique = true)
    private String title;
    @Column(nullable = false)
    private Boolean pinned;
    @ManyToMany
    @JoinTable(name = "compilations_events",
            joinColumns = @JoinColumn(name = "compilation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Column(name = "id")
    private List<Event> events;
}
