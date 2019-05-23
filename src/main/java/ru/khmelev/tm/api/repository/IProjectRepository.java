package ru.khmelev.tm.api.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.khmelev.tm.entity.Project;

import javax.persistence.QueryHint;
import java.util.Collection;

@Repository
public interface IProjectRepository extends JpaRepository<Project, String> {

    @NotNull
    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"))
    @Query(value = "Select project from Project project where project.id = :id and userId = :userId")
    Project findOne(@NotNull @Param("id") final String id, @NotNull @Param("userId") final String userId);

    @NotNull
    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"))
    @Query(value = "Select project from Project project where userId = :userId")
    Collection<Project> findAll(@NotNull @Param("userId") final String userId);

    @Modifying
    @Query(value = "DELETE FROM Project WHERE userId = :userId")
    void removeAll(@NotNull @Param("userId") final String userId);
}