package com.vass.jhipsterapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Editorial.
 */
@Entity
@Table(name = "editorial")
public class Editorial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "editorial")
    @JsonIgnore
    private Set<Book> books = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Editorial nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public Editorial books(Set<Book> books) {
        this.books = books;
        return this;
    }

    public Editorial addBook(Book book) {
        this.books.add(book);
        book.setEditorial(this);
        return this;
    }

    public Editorial removeBook(Book book) {
        this.books.remove(book);
        book.setEditorial(null);
        return this;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Editorial editorial = (Editorial) o;
        if (editorial.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, editorial.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Editorial{" +
            "id=" + id +
            ", nombre='" + nombre + "'" +
            '}';
    }
}
