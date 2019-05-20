package ru.khmelev.tm.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import ru.khmelev.tm.api.repository.IUserRepository;
import ru.khmelev.tm.dto.UserDTO;
import ru.khmelev.tm.exception.RepositoryException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Repository
public class UserRepository implements IUserRepository {

    @NotNull private static Map<String, UserDTO> userMap = new HashMap<>();

    @Override
    public void persist(@NotNull String id, @NotNull UserDTO userDTO) {
        if (!id.isEmpty()) {
            userMap.put(id, userDTO);
        }
    }

    @NotNull
    @Override
    public UserDTO findOne(@NotNull final String id) {
        if (!id.isEmpty()) {
            for (UserDTO userDTO : userMap.values()) {
                if (userDTO.getId().equals(id)) {
                    return userDTO;
                }
            }
        }
        throw new RepositoryException();
    }

    @NotNull
    @Override
    public Collection<UserDTO> findAll() {
        return userMap.values();
    }

    @Override
    public void merge(@NotNull final String id, @NotNull final UserDTO userDTO) {
    }

    @Override
    public void remove(@NotNull final String id) {
        if (!id.isEmpty()) {
            Collection<UserDTO> list = userMap.values();
            Iterator<UserDTO> it = list.iterator();
            while (it.hasNext()) {
                UserDTO userDTO = it.next();
                if (userDTO.getId().equals(id)) {
                    it.remove();
                    return;
                }
            }
        }
    }

    @Override
    public void removeAll() {
        userMap.clear();
    }
}