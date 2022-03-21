package com.example.jakartaee.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Entity
@Table(name = "order")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Order implements Serializable {

    @Id
    @GeneratedValue
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