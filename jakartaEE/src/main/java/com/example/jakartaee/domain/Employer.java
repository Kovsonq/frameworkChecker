package com.example.jakartaee.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.NamedQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "employer")
@Getter
@Setter
@NoArgsConstructor
@NamedQuery(name = "Employer.findAll", query = "Select e from Employer e")
@NamedQuery(name = "Employer.findByName", query = "Select e from Employer e where e.name = :name")
@NamedQuery(name = "Employer.findByEmail", query = "Select e from Employer e where e.email = :email")
public class Employer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="password")
    private char[] password;

    @Column(name="phone")
    private String phone;

    @Column(name="email")
    private String email;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "employer_service",
            joinColumns = { @JoinColumn(name = "employer_id") },
            inverseJoinColumns = { @JoinColumn(name = "service_id") }
    )
    private List<Service> services;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "employer", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Order> orders;

    public Employer(String name, char[] password, String phone, String email, Company company) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employer employer = (Employer) o;
        return Objects.equals(name, employer.name) && Arrays.equals(password, employer.password) && Objects.equals(phone, employer.phone) && Objects.equals(email, employer.email);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, phone, email);
        result = 31 * result + Arrays.hashCode(password);
        return result;
    }
}