package ru.khmelev.tm.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;
import ru.khmelev.tm.api.repository.IUserRepository;
import ru.khmelev.tm.dto.UserDTO;
import ru.khmelev.tm.entity.User;
import ru.khmelev.tm.exception.RepositoryException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Repository
public class UserRepository implements IUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void persist(@NotNull final User user) {
        entityManager.persist(user);
    }

    @Override
    @Nullable
    public User findOne(@NotNull final String id) {
        @NotNull final String query = "SELECT user FROM User user WHERE id = :id";
        @NotNull final TypedQuery<User> typedQuery = entityManager.createQuery(query, User.class);
        typedQuery.setParameter("id", id);
        return typedQuery.getSingleResult();
    }

    @Override
    @Nullable
    public Collection<User> findAll() {
        @NotNull final String query = "Select user from User user";
        @NotNull final TypedQuery<User> typedQuery = entityManager.createQuery(query, User.class);
        return typedQuery.getResultList();
    }

    @Override
    public void merge(@NotNull final User user) {
        entityManager.merge(user);
    }

    @Override
    public void remove(@NotNull final User user) {
        entityManager.remove(user);
    }

    @Override
    public void removeAll(@NotNull final String userId) {
        throw new RepositoryException();
    }
}