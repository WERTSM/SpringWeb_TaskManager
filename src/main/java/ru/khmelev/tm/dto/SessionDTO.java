package ru.khmelev.tm.dto;

import lombok.Getter;
import lombok.Setter;
import ru.khmelev.tm.api.dto.IdentifiableDTO;

import java.util.Date;

@Getter
@Setter
public class SessionDTO implements IdentifiableDTO {

    private String id;
    private String userId;
    private String signature;
    private Date dateCreate;

    public SessionDTO() {
        this.id = "";
        this.signature = "";
        this.userId = "";
        this.dateCreate = new Date();
    }
}