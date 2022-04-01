package com.example.jakartaee.domain;

import jakarta.persistence.NamedQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "service")
@Getter
@Setter
@NoArgsConstructor
@NamedQuery(name = "Service.findAll", query = "Select s from Service s")
public class Service implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @ManyToMany(mappedBy = "services")
    private List<Employer> employers;

    public Service(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return Objects.equals(name, service.name) && Objects.equals(description, service.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

}