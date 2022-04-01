package com.example.jakartaee.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.NamedQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "company")
@Getter
@Setter
@NoArgsConstructor
@NamedQuery(name = "Company.findAll", query = "Select c from Company c")
@NamedQuery(name = "Company.findByName", query = "Select c from Company c where c.name = :name")
public class Company implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="type")
    private String type;

    @OneToMany(mappedBy = "company", cascade = CascadeType.MERGE)
    private List<Employer> employers;

    public Company(String name, String type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(name, company.name) && Objects.equals(type, company.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}