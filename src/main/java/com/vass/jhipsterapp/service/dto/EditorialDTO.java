package com.vass.jhipsterapp.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Editorial entity.
 */
public class EditorialDTO implements Serializable {

    private Long id;

    private String old;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getOld() {
        return old;
    }

    public void setOld(String old) {
        this.old = old;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EditorialDTO editorialDTO = (EditorialDTO) o;

        if ( ! Objects.equals(id, editorialDTO.id)) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "EditorialDTO{" +
            "id=" + id +
            ", old='" + old + "'" +
            '}';
    }
}
