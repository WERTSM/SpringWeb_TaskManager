package ru.khmelev.tm.api.dto;

import ru.khmelev.tm.enumeration.Status;

import java.util.Date;

public interface EntityDTO extends IdentifiableDTO {

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    Date getDateStart();

    void setDateStart(Date dateStart);

    Date getDateFinish();

    void setDateFinish(Date dateFinish);

    Date getDateCreate();

    void setDateCreate(Date dateCreate);

    Status getStatus();

    void setStatus(Status status);

    String getUserId();

    void setUserId(String userId);
}