package ru.khmelev.tm.api.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.khmelev.tm.entity.User;

import javax.persistence.QueryHint;
import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {

    @Nullable
    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"))
    @Query(value = "SELECT user FROM User user WHERE id = :id")
    User findOne(@NotNull @Param("id") final String id);

    @Nullable
    @QueryHints(@QueryHint(name = org.hibernate.jpa.QueryHints.HINT_CACHEABLE, value = "true"))
    @Query(value = "Select user from User user")
    List<User> findAll();

    @Nullable
    @Query(value = "SELECT user FROM User user WHERE user.login = :login")
    User findByLogin(@NotNull @Param("login") final String login);
}