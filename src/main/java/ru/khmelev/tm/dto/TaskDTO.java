package ru.khmelev.tm.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.khmelev.tm.api.dto.EntityDTO;
import ru.khmelev.tm.enumeration.Status;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class TaskDTO implements Serializable, EntityDTO {

    private String id;

    private String name;

    private String description;

    private Date dateStart;

    private Date dateFinish;

    private String projectId;

    private Status status;

    private Date dateCreate;

    private String userId;
}