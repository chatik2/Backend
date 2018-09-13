package com.gmail.ribil39.model;

import java.util.Objects;

public class UserDTO {
    private String name;
    private Long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(name, userDTO.name) &&
                Objects.equals(id, userDTO.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, id);
    }
}
