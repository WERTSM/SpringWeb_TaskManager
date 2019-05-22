package ru.khmelev.tm.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@Setter
@Getter
@MappedSuperclass
public abstract class Entity extends Identifiable {

    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinColumn(name = "userId")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}