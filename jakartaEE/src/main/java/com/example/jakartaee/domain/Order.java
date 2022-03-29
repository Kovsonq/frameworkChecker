package com.example.jakartaee.domain;

import jakarta.persistence.NamedQuery;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
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
import java.util.UUID;

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
    @FutureOrPresent
    @NotNull
    private ZonedDateTime startDate;

    @Column(name="endDate", updatable = false)
    private ZonedDateTime endDate;

    @Column(name="duration", updatable = false)
    @NotNull
    private Long duration;

    @Column(name="description")
    private String description;

    @Column(name="booked")
    private boolean booked;

    @Column(name="scheduleId")
    private UUID scheduleId;

    @OneToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "employer_id")
    @NotNull
    private Employer employer;

    public Order(ZonedDateTime startDate, Long duration, String description) {
        this.startDate = startDate;
        this.endDate = ChronoUnit.MINUTES.addTo(startDate, duration);
        this.description = description;
        this.duration = duration;
        this.booked = false;
        this.status = new Status();
    }

    public Order(ZonedDateTime startDate, ZonedDateTime endDate, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.duration = ChronoUnit.MINUTES.between(startDate, endDate);
        this.booked = false;
        this.status = new Status();
    }

    public Order(ZonedDateTime startDate, Long duration) {
        this.startDate = startDate;
        this.endDate = ChronoUnit.MINUTES.addTo(startDate, duration);
        this.duration = duration;
        this.booked = false;
        this.status = new Status();
    }

    public Order(ZonedDateTime startDate, Long duration, UUID uuid) {
        this.startDate = startDate;
        this.scheduleId = uuid;
        this.endDate = ChronoUnit.MINUTES.addTo(startDate, duration);
        this.duration = duration;
        this.booked = false;
        this.status = new Status();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(startDate, order.startDate) && Objects.equals(endDate, order.endDate) &&
                Objects.equals(duration, order.duration) && Objects.equals(description, order.description) &&
                Objects.equals(status, order.status) && Objects.equals(user, order.user) &&
                Objects.equals(employer, order.employer) &&
                Objects.equals(booked, order.booked);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, duration, description, status, user, employer, booked);
    }

}