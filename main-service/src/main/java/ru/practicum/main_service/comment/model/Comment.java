package ru.practicum.main_service.comment.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.practicum.main_service.event.model.Event;
import ru.practicum.main_service.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments", schema = "public")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private LocalDateTime created;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User author;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Event event;
    @Enumerated(EnumType.STRING)
    private CommentStatus status;

}
