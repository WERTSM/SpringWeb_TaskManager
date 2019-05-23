package ru.khmelev.tm.api.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.khmelev.tm.entity.Task;

import javax.persistence.QueryHint;
import java.util.Collection;

@Repository
public interface ITaskRepository extends JpaRepository<Task, String> {

    @NotNull
    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"))
    @Query(value = "Select task from Task task where task.id = :id and userId = :userId")
    Task findOne(@NotNull @Param("id") final String id, @NotNull @Param("userId") final String userId);

    @NotNull
    @Query(value = "Select task from Task task where userId = :userId")
    Collection<Task> findAll(@NotNull @Param("userId") final String userId);

    @Modifying
    @Query(value = "DELETE FROM Task WHERE userId = :userId")
    void removeAll(@NotNull @Param("userId") final String userId);

    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"))
    @Query(value = "Select task from Task task where projectId = :projectId and userId = :userId")
    Collection<Task> findAllTaskFromProject(@NotNull @Param("projectId") final String projectId, @NotNull @Param("userId") final String userId);
}