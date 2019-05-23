package ru.khmelev.tm.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.khmelev.tm.api.repository.IProjectRepository;
import ru.khmelev.tm.api.repository.ITaskRepository;
import ru.khmelev.tm.api.repository.IUserRepository;
import ru.khmelev.tm.api.service.ITaskService;
import ru.khmelev.tm.dto.TaskDTO;
import ru.khmelev.tm.entity.Task;
import ru.khmelev.tm.exception.ServiceException;

import java.util.*;

@Service
@Transactional
public class TaskService implements ITaskService {

    @Autowired
    private
    ITaskRepository taskRepository;

    @Autowired
    private
    IProjectRepository projectRepository;

    @Autowired
    private
    IUserRepository userRepository;

    @Override
    public void createTask(@NotNull final String id, @NotNull final TaskDTO taskDTO) {
        @NotNull final Task task = new Task();
        task.setId(id);
        fromDTOToTask(taskDTO, task);

        taskRepository.save(task);
    }

    @Nullable
    @Override
    public TaskDTO findTask(@NotNull final String id, @NotNull final String userId) {
        if (id.isEmpty() || userId.isEmpty()) throw new ServiceException();
        @NotNull final Task task = Objects.requireNonNull(taskRepository.findOne(id, userId));
        return fromTaskToDTO(task);
    }

    @NotNull
    @Override
    public Collection<TaskDTO> findAll(@NotNull final String userId) {
        if (userId.isEmpty()) throw new ServiceException();

        @NotNull final Collection<Task> list = Objects.requireNonNull(taskRepository.findAll(userId));
        @NotNull final List<TaskDTO> listDTO = new ArrayList<>();
        for (Task task : list) {
            listDTO.add(fromTaskToDTO(task));
        }
        return listDTO;
    }

    @Override
    public void editTask(@NotNull final String id, @NotNull TaskDTO taskDTO, @NotNull final String userId) {
        if (id.isEmpty() || userId.isEmpty()) throw new ServiceException();

        @NotNull final Task task = Objects.requireNonNull(taskRepository.findOne(id, userId));
        taskRepository.save(fromDTOToTask(taskDTO, task));
    }

    @NotNull
    @Override
    public Collection<TaskDTO> findAllName(@NotNull final String findParameter, @NotNull final String userId) {
        if (findParameter.isEmpty() || userId.isEmpty()) throw new ServiceException();

        @NotNull final List<Task> list = new ArrayList<>(Objects.requireNonNull(taskRepository.findAll(userId)));
        @NotNull final Iterator<Task> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().getName().contains(findParameter)) {
                iterator.remove();
            }
        }
        @NotNull final List<TaskDTO> listDTO = new ArrayList<>();
        for (Task task : list) {
            listDTO.add(fromTaskToDTO(task));
        }
        return listDTO;
    }

    @NotNull
    @Override
    public Collection<TaskDTO> findAllDescription(@NotNull final String findParameter, @NotNull final String userId) {
        if (findParameter.isEmpty() || userId.isEmpty()) throw new ServiceException();

        @NotNull final List<Task> list = new ArrayList<>(Objects.requireNonNull(taskRepository.findAll(userId)));
        @NotNull final Iterator<Task> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().getDescription().contains(findParameter)) {
                iterator.remove();
            }
        }
        @NotNull final List<TaskDTO> listDTO = new ArrayList<>();
        for (Task task : list) {
            listDTO.add(fromTaskToDTO(task));
        }
        return listDTO;
    }

    @Override
    public void removeTask(@NotNull final String id, @NotNull final String userId) {
        if (id.isEmpty() || userId.isEmpty()) throw new ServiceException();

        taskRepository.delete(Objects.requireNonNull(taskRepository.findOne(id, userId)));
    }

    @Override
    public void clearTask(@NotNull final String userId) {
        if (userId.isEmpty()) throw new ServiceException();

        taskRepository.removeAll(userId);
    }

    @NotNull
    @Override
    public Collection<TaskDTO> listTaskFromProject(@NotNull String projectId, @NotNull String userId) {
        if (projectId.isEmpty() || userId.isEmpty()) throw new ServiceException();

        @NotNull final Collection<Task> list = Objects.requireNonNull(taskRepository.findAllTaskFromProject(projectId, userId));

        @NotNull final List<TaskDTO> listDTO = new ArrayList<>();
        for (Task task : list) {
            listDTO.add(fromTaskToDTO(task));
        }
        return listDTO;
    }

    @NotNull
    private Task fromDTOToTask(@NotNull final TaskDTO taskDTO, @NotNull final Task task) {
        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task.setDateStart(taskDTO.getDateStart());
        task.setDateFinish(taskDTO.getDateFinish());
        task.setDateCreate(taskDTO.getDateCreate());
        task.setStatus(taskDTO.getStatus());
        if (taskDTO.getProjectId().isEmpty()) {
            task.setProject(null);
        } else {
            task.setProject(projectRepository.findOne(taskDTO.getProjectId(), taskDTO.getUserId()));
        }
        task.setUser(userRepository.findOne(taskDTO.getUserId()));
        return task;
    }

    @NotNull
    private TaskDTO fromTaskToDTO(@NotNull final Task task) {
        @NotNull final TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setName(task.getName());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setDateStart(task.getDateStart());
        taskDTO.setDateFinish(task.getDateFinish());
        taskDTO.setDateCreate(task.getDateCreate());
        taskDTO.setStatus(task.getStatus());
        if (task.getProject() == null) {
            taskDTO.setProjectId("");
        } else {
            taskDTO.setProjectId(task.getProject().getId());
        }
        taskDTO.setUserId(task.getUser().getId());
        return taskDTO;
    }
}