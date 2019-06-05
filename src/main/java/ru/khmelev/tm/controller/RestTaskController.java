package ru.khmelev.tm.controller;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.khmelev.tm.api.service.ITaskService;
import ru.khmelev.tm.dto.TaskDTO;

import javax.servlet.http.HttpSession;
import java.util.Collection;

@RestController
public class RestTaskController {

    @Autowired
    private ITaskService taskService;

    @PostMapping(value = "/taskCreate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean taskCreate(@RequestBody @NotNull final TaskDTO taskDTO) {
        taskService.createTask(taskDTO.getId(), taskDTO);
        return true;
    }

    @Nullable
    @GetMapping(value = "/findTask/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskDTO findTask(final HttpSession session, @PathVariable(value = "id") @NotNull final String id) {
        return taskService.findTask(id, (String) session.getAttribute("userId"));
    }

    @NotNull
    @GetMapping(value = "/findAllTask", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<TaskDTO> findAllTask(final HttpSession session) {
        return taskService.findAll((String) session.getAttribute("userId"));
    }

    @PutMapping(value = "/taskEdit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean taskEdit(final HttpSession session, @RequestBody @NotNull final TaskDTO taskDTO) {
        taskService.editTask(taskDTO.getId(), taskDTO, (String) session.getAttribute("userId"));
        return true;
    }

    @DeleteMapping(value = "/taskDelete/{id}")
    public Boolean taskDelete(final HttpSession session, @PathVariable(value = "id") @NotNull final String id) {
        taskService.removeTask(id, (String) session.getAttribute("userId"));
        return true;
    }
}