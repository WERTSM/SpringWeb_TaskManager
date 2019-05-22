package ru.khmelev.tm.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;
import ru.khmelev.tm.api.repository.IProjectRepository;
import ru.khmelev.tm.dto.ProjectDTO;
import ru.khmelev.tm.entity.Project;
import ru.khmelev.tm.exception.RepositoryException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;

@Repository
public class ProjectRepository implements IProjectRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void persist(@NotNull final Project project) {
        entityManager.persist(project);
    }

    @Override
    @Nullable
    public Project findOne(@NotNull final String id, @NotNull final String userId) {
        @NotNull final String query = "Select project from Project project where project.id = :id and userId = :userId";
        @NotNull final TypedQuery<Project> typedQuery = entityManager.createQuery(query, Project.class);
        typedQuery.setParameter("id", id);
        typedQuery.setParameter("userId", userId);
        return typedQuery.getSingleResult();
    }

    @Override
    @Nullable
    public Collection<Project> findAll(@NotNull final String userId) {
        @NotNull final String query = "Select project from Project project where userId = :userId";
        @NotNull final TypedQuery<Project> typedQuery = entityManager.createQuery(query, Project.class);
        typedQuery.setParameter("userId", userId);
        return typedQuery.getResultList();
    }

    @Override
    public void merge(@NotNull final Project project) {
        entityManager.merge(project);
    }

    @Override
    public void remove(@NotNull final Project project) {
        entityManager.remove(project);
    }

    @Override
    public void removeAll(@NotNull final String userId) {

//        @NotNull final String query = "Delete from Project project where project.userId = :userId";
//        @NotNull final TypedQuery<Project> typedQuery = entityManager.createQuery(query, Project.class);
//        typedQuery.setParameter("userId", userId);

        for (Project project : Objects.requireNonNull(findAll(userId))) {
            remove(project);
        }
    }
}