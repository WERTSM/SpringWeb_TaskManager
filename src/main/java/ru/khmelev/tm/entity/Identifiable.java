package ru.khmelev.tm.entity;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Setter
@Getter
@MappedSuperclass
public abstract class Identifiable {

    @Id
    @Nullable
    private String id;
}