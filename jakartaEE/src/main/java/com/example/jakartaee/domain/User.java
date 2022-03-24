package com.example.jakartaee.domain;

import jakarta.persistence.NamedQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "\"user\"")
@Getter
@Setter
@ToString
@NoArgsConstructor
@NamedQuery(name = "User.findAll", query = "Select u from User u")
@NamedQuery(name = "User.findByName", query = "Select u from User u where u.name = :name")
public class User implements Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Order> orders = new ArrayList<>();

    public User(String name, char[] password, String phone, String email) {
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Arrays.equals(password, user.password) && Objects.equals(phone, user.phone) && Objects.equals(email, user.email) && Objects.equals(orders, user.orders);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, phone, email, orders);
        result = 31 * result + Arrays.hashCode(password);
        return result;
    }

}
