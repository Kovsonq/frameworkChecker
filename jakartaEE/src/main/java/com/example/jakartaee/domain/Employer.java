package com.example.jakartaee.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "employer")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Employer implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="password")
    private char[] password;

    @Column(name="phone")
    private String phone;

    @Column(name="email")
    private String email;

    public Employer(String name, char[] password, String phone, String email, Company company) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.company = company;
    }

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "employer_services",
            joinColumns = @JoinColumn(name = "employer_id", referencedColumnName = "services_id"))
    private List<Service> services = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "employer")
    private List<Order> orders = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employer employer = (Employer) o;
        return Objects.equals(name, employer.name) && Arrays.equals(password, employer.password) && Objects.equals(phone, employer.phone) && Objects.equals(email, employer.email) && Objects.equals(company, employer.company) && Objects.equals(services, employer.services) && Objects.equals(orders, employer.orders);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, phone, email, company, services, orders);
        result = 31 * result + Arrays.hashCode(password);
        return result;
    }

}