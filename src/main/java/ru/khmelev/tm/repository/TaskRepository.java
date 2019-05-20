package ru.khmelev.tm.repository;

import org.jetbrains.annotations.NotNull;
import ru.khmelev.tm.api.repository.ITaskRepository;
import ru.khmelev.tm.dto.TaskDTO;
import ru.khmelev.tm.exception.RepositoryException;

import java.util.*;

public class TaskRepository implements ITaskRepository {

    private static final ITaskRepository _instance = new TaskRepository();

    private static Map<String, TaskDTO> taskMap = new HashMap<>();

    public static ITaskRepository getInstance() {
        return _instance;
    }

    @Override
    public void persist(@NotNull final String id, @NotNull final TaskDTO taskDTO) {
        if (!id.isEmpty()) {
            taskMap.put(id, taskDTO);
        }
    }

    @NotNull
    @Override
    public TaskDTO findOne(@NotNull final String id, @NotNull final String userId) {
        if (!id.isEmpty() && !userId.isEmpty()) {
            final Collection<TaskDTO> list = new ArrayList<>(findAll(userId));
            for (TaskDTO taskDTO : list) {
                if (taskDTO.getId().equals(id)) {
                    return taskDTO;
                }
            }
        }
        throw new RepositoryException();
    }

    @NotNull
    @Override
    public Collection<TaskDTO> findAll(@NotNull final String userId) {
        if (!userId.isEmpty()) {
            Collection<TaskDTO> list = new ArrayList<>(taskMap.values());
            Iterator<TaskDTO> it = list.iterator();
            while (it.hasNext()) {
                TaskDTO taskDTO = it.next();
                if (!taskDTO.getUserId().equals(userId)) {
                    it.remove();
                }
            }
            return list;
        }
        throw new RepositoryException();
    }

    @Override
    public void merge(@NotNull final String id, @NotNull final TaskDTO taskDTO, @NotNull final String userId) {
    }


    @Override
    public void remove(@NotNull final String id, @NotNull final String userId) {
        if (!id.isEmpty() && !userId.isEmpty()) {
            Collection<TaskDTO> list = taskMap.values();
            Iterator<TaskDTO> it = list.iterator();
            while (it.hasNext()) {
                TaskDTO taskDTO = it.next();
                if (taskDTO.getId().equals(id) && taskDTO.getUserId().equals(userId)) {
                    it.remove();
                    return;
                }
            }
        }
    }

    @Override
    public void removeAll(@NotNull final String userId) {
        if (!userId.isEmpty()) {
            Collection<TaskDTO> list = taskMap.values();
            list.clear();
        }
    }
}