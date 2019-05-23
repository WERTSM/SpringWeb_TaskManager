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
import java.util.List;

@Setter
@Getter
@javax.persistence.Entity
@Table(name = "project")
@NoArgsConstructor
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Project extends Entity implements Serializable {

    private String name;

    private String description;

    @Temporal(value = TemporalType.DATE)
    private Date dateStart;

    @Temporal(value = TemporalType.DATE)
    private Date dateFinish;

    @Temporal(value = TemporalType.DATE)
    private Date dateCreate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Nullable
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Task> tasks;
}