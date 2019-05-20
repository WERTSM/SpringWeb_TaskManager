package ru.khmelev.tm.service;

import org.jetbrains.annotations.NotNull;
import ru.khmelev.tm.api.repository.ITaskRepository;
import ru.khmelev.tm.api.service.ITaskService;
import ru.khmelev.tm.dto.TaskDTO;
import ru.khmelev.tm.exception.ServiceException;
import ru.khmelev.tm.repository.TaskRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class TaskService implements ITaskService {

    private static final ITaskService _instance = new TaskService();

    @NotNull
    private final ITaskRepository taskRepository = TaskRepository.getInstance();

    public static ITaskService getInstance() {
        return _instance;
    }

    @Override
    public void createTask(@NotNull final String id, @NotNull final TaskDTO taskDTO) {
        taskRepository.persist(id, taskDTO);
    }

    @NotNull
    @Override
    public TaskDTO findTask(@NotNull final String id, @NotNull final String userId) {
        if (!id.isEmpty() && !userId.isEmpty()) {
            return taskRepository.findOne(id, userId);
        }
        throw new ServiceException();
    }

    @NotNull
    @Override
    public Collection<TaskDTO> findAll(@NotNull final String userId) {
        return taskRepository.findAll(userId);
    }

    @Override
    public void editTask(@NotNull final String id, @NotNull TaskDTO taskDTO, @NotNull final String userId) {
        if (!id.isEmpty() && !userId.isEmpty()) {
            taskRepository.merge(id, taskDTO, userId);
        }
    }

    @Override
    public void removeTask(@NotNull final String id, @NotNull final String userId) {
        if (!id.isEmpty() && !userId.isEmpty()) {
            taskRepository.remove(id, userId);
        }
    }

    @Override
    public void clearTask(@NotNull final String userId) {
        if (!userId.isEmpty()) {
            taskRepository.removeAll(userId);
        }
    }

    @NotNull
    @Override
    public List<TaskDTO> listTaskFromProject(@NotNull final String projectId, @NotNull final String userId) {
        if (!projectId.isEmpty() && !userId.isEmpty()) {
            List<TaskDTO> list = new ArrayList<>(taskRepository.findAll(userId));
            Iterator<TaskDTO> it = list.iterator();
            while (it.hasNext()) {
                TaskDTO taskDTO = it.next();
                if (!taskDTO.getProjectId().equals(projectId)) {
                    it.remove();
                }
            }
            return list;
        }
        throw new ServiceException();
    }

    @Override
    public void removeAllTaskFromProject(@NotNull final String projectId, @NotNull final String userId) {
        if (!projectId.isEmpty() && !userId.isEmpty()) {
            List<TaskDTO> list = new ArrayList<>(taskRepository.findAll(userId));
            Iterator<TaskDTO> it = list.iterator();
            while (it.hasNext()) {
                TaskDTO taskDTO = it.next();
                if (taskDTO.getProjectId().equals(projectId)) {
                    taskRepository.remove(taskDTO.getId(), userId);
                }
            }
        }
    }
}