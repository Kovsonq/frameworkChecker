package com.example.jakartaee.domain;

import jakarta.persistence.NamedQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Entity
@Table(name = "\"order\"")
@Getter
@Setter
@ToString
@NoArgsConstructor
@NamedQuery(name = "Order.findAll", query = "Select o from Order o")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="startDate", updatable = false)
    private ZonedDateTime startDate;

    @Column(name="endDate", updatable = false)
    private ZonedDateTime endDate;

    @Column(name="duration", updatable = false)
    private Long duration;

    @Column(name="description")
    private String description;

    @OneToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "employer_id")
    private Employer employer;

    public Order(ZonedDateTime startDate, ZonedDateTime endDate, String description, User user, Employer employer) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.user = user;
        this.employer = employer;
        this.duration = ChronoUnit.MINUTES.between(startDate, endDate);
        this.status = new Status(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(startDate, order.startDate) && Objects.equals(endDate, order.endDate) && Objects.equals(duration, order.duration) && Objects.equals(description, order.description) && Objects.equals(status, order.status) && Objects.equals(user, order.user) && Objects.equals(employer, order.employer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, duration, description, status, user, employer);
    }

}