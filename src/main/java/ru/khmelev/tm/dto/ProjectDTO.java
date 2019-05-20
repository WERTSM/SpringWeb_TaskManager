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
public class ProjectDTO implements Serializable, EntityDTO {

    private String id;

    private String name;

    private String description;

    private Date dateStart;

    private Date dateFinish;

    private Date dateCreate;

    private Status status;

    private String userId;
}