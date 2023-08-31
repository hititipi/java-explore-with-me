package ru.practicum.main_service.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.main_service.event.model.Event;
import ru.practicum.main_service.event.model.EventState;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByInitiatorId(Long userId, Pageable pageable);

    Optional<Event> findByIdAndInitiatorId(Long eventId, Long userId);

    List<Event> findAllByIdIn(List<Long> eventsId);

    List<Event> findAllByCategoryId(long catId);


    @Query("select e from Event e " +
            "where (coalesce(:userIds, null) is null or e.initiator.id in :userIds) " +
            "and (coalesce(:states, null) is null or e.state in :states) " +
            "and (coalesce(:categoryIds, null) is null or e.category.id in :categoryIds) " +
            "and (coalesce(:rangeStart, null) is null or e.eventDate >= :rangeStart) " +
            "and (coalesce(:rangeEnd, null) is null or e.eventDate <= :rangeEnd) ")
    List<Event> findByAdmin(Pageable pageable,
                            @Param("userIds") List<Long> userIds,
                            @Param("states") List<EventState> states,
                            @Param("categoryIds") List<Long> categoryIds,
                            @Param("rangeStart") LocalDateTime rangeStart,
                            @Param("rangeEnd") LocalDateTime rangeEnd
                            );

    /*@Query("FROM Event AS e WHERE (:users IS NULL OR e.initiator.id IN (:users)) " +
            "AND (:states IS NULL OR CAST(e.state AS string ) IN (:states)) " +
            "AND (:categories IS NULL OR e.category.id IN (:categories) ) " +
            "AND (:rangeStart IS NULL OR e.eventDate >= CAST(:rangeStart AS timestamp) ) " +
            "AND (:rangeEnd IS NULL OR e.eventDate <= CAST( :rangeEnd AS timestamp ) )")
    List<Event> findAllByAdmin(Pageable page,
                               List<Long> users,
                               List<EventState> states,
                               List<Long> categories,
                               LocalDateTime rangeStart,
                               LocalDateTime rangeEnd);*/

    @Query("select e from Event e " +
            "where e.state = 'PUBLISHED' " +
            "and (coalesce(:text, null) is null or (lower(e.annotation) like lower(concat('%', :text, '%')) or lower(e.description) like lower(concat('%', :text, '%')))) " +
            "and (coalesce(:categoryIds, null) is null or e.category.id in :categoryIds) " +
            "and (coalesce(:paid, null) is null or e.paid = :paid) " +
            "and e.eventDate >= :rangeStart " +
            "and (coalesce(:rangeEnd, null) is null or e.eventDate <= :rangeEnd) " +
            "and (:onlyAvailable = false or e.id in " +
            "(select r.event.id " +
            "from Request r " +
            "where r.status = 'CONFIRMED' " +
            "group by r.event.id " +
            "having e.participantLimit - count(id) > 0 " +
            "order by count(r.id))) ")
    List<Event> findAllByPublic( Pageable pageable,
                               @Param("text") String text,
                               @Param("categoryIds") List<Long> categoryIds,
                              @Param("paid") Boolean paid, @Param("rangeStart") LocalDateTime rangeStart,
                              @Param("rangeEnd") LocalDateTime rangeEnd, @Param("onlyAvailable") Boolean onlyAvailable
                             );

   /* @Query("FROM Event AS e WHERE (:text IS NULL OR LOWER(e.annotation) LIKE LOWER(CONCAT('%', :text, '%')) OR LOWER(e.description) LIKE LOWER(CONCAT('%', :text, '%'))) " +
            "AND (:categories IS NULL OR e.category.id IN (:categories)) " +
            "AND (:paid IS NULL OR e.paid IN :paid) " +
            "AND (:rangeStart IS NULL OR e.eventDate >= CAST(:rangeStart AS timestamp )) " +
            "AND (:rangeEnd IS NULL OR e.eventDate <= CAST(:rangeEnd AS timestamp ) ) " +
            "AND (:onlyAvailable = FALSE OR (e.participantLimit = 0 OR e.requestModeration = FALSE)) " +
            "AND (e.state = 'PUBLISHED') " +
            "AND ((:rangeStart IS NULL AND :rangeEnd IS NULL) OR (e.eventDate >= now()) )")
    List<Event> findAllByPublic(Pageable page,
                                String text,
                                List<Long> categories,
                                Boolean paid,
                                LocalDateTime rangeStart,
                                LocalDateTime rangeEnd,
                                Boolean onlyAvailable);*/
}