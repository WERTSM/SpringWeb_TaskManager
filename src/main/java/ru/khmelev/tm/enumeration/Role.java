package ru.khmelev.tm.enumeration;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ADMIN("ADMIN"), USER("USER");

    String displayName;

    Role(@NotNull final String displayName) {
        this.displayName = displayName;
    }

    @NotNull
    public String displayName() {
        return displayName;
    }

    @Override
    public String getAuthority() {
        return displayName;
    }
}