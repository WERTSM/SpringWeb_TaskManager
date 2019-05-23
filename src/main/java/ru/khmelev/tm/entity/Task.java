package ru.khmelev.tm.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jetbrains.annotations.Nullable;
import ru.khmelev.tm.enumeration.Status;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@javax.persistence.Entity
@Table(name = "task")
@NoArgsConstructor
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Task extends Entity implements Serializable {

    private String name;

    private String description;

    @Temporal(value = TemporalType.DATE)
    private Date dateStart;

    @Temporal(value = TemporalType.DATE)
    private Date dateFinish;

    @Nullable
    @JoinColumn(name = "projectId")
    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Temporal(value = TemporalType.DATE)
    private Date dateCreate;
}