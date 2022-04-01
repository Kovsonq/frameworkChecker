package com.example.jakartaee.domain;

import com.example.jakartaee.domain.values.OrderStatus;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.FetchType;
import jakarta.persistence.NamedQuery;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "status")
@Getter
@NoArgsConstructor
@NamedQuery(name = "Status.findAll", query = "Select s from Status s")
public class Status implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="createdTime", updatable = false)
    private ZonedDateTime createdTime;

    @Column(name="requestedTime")
    private ZonedDateTime requestedTime;
    @Column(name="approvedTime")
    private ZonedDateTime approvedTime;
    @Column(name="canceledTime")
    private ZonedDateTime canceledTime;

    @Column(name="startedTime")
    private ZonedDateTime startedTime;
    @Column(name="finishedTime")
    private ZonedDateTime finishedTime;

    @Column(name="closedTime")
    private ZonedDateTime closedTime;

    @Column(name="lastChangedTime")
    private ZonedDateTime lastChangedTime;

    @Column(name="orderStatus")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonbTransient
    private Order order;

    public Status(Order order) {
        this.createdTime = ZonedDateTime.now();
        this.lastChangedTime = ZonedDateTime.now();
        this.orderStatus = OrderStatus.CREATED;
        this.order = order;
    }

    public Status(ZonedDateTime createdTime) {
        this.createdTime = createdTime;
        this.lastChangedTime = ZonedDateTime.now();
        this.orderStatus = OrderStatus.CREATED;
    }

    public void setRequestedTime() {
        this.requestedTime = ZonedDateTime.now();
        this.lastChangedTime = ZonedDateTime.now();
        this.orderStatus = OrderStatus.REQUESTED;
    }

    public void setRequestedTime(ZonedDateTime requestedTime) {
        this.requestedTime = requestedTime;
        this.lastChangedTime = ZonedDateTime.now();
        this.orderStatus = OrderStatus.REQUESTED;
    }

    public void setApprovedTime() {
        this.approvedTime = ZonedDateTime.now();
        this.lastChangedTime = ZonedDateTime.now();
        this.orderStatus = OrderStatus.APPROVED;
    }

    public void setApprovedTime(ZonedDateTime approvedTime) {
        this.approvedTime = approvedTime;
        this.lastChangedTime = ZonedDateTime.now();
        this.orderStatus = OrderStatus.APPROVED;
    }

    public void setCanceledTime() {
        this.canceledTime = ZonedDateTime.now();
        this.lastChangedTime = ZonedDateTime.now();
        this.orderStatus = OrderStatus.CANCELED;
    }

    public void setCanceledTime(ZonedDateTime canceledTime) {
        this.canceledTime = canceledTime;
        this.lastChangedTime = ZonedDateTime.now();
        this.orderStatus = OrderStatus.CANCELED;
    }

    public void setStartedTime() {
        this.startedTime = ZonedDateTime.now();
        this.lastChangedTime = ZonedDateTime.now();
        this.orderStatus = OrderStatus.STARTED;
    }

    public void setStartedTime(ZonedDateTime startedTime) {
        this.startedTime = startedTime;
        this.lastChangedTime = ZonedDateTime.now();
        this.orderStatus = OrderStatus.STARTED;
    }

    public void setFinishedTime() {
        this.finishedTime = ZonedDateTime.now();
        this.lastChangedTime = ZonedDateTime.now();
        this.orderStatus = OrderStatus.FINISHED;
    }

    public void setFinishedTime(ZonedDateTime finishedTime) {
        this.finishedTime = finishedTime;
        this.lastChangedTime = ZonedDateTime.now();
        this.orderStatus = OrderStatus.FINISHED;
    }

    public void setClosedTime() {
        this.closedTime = ZonedDateTime.now();
        this.lastChangedTime = ZonedDateTime.now();
        this.orderStatus = OrderStatus.CLOSED;
    }

    public void setClosedTime(ZonedDateTime closedTime) {
        this.closedTime = closedTime;
        this.lastChangedTime = ZonedDateTime.now();
        this.orderStatus = OrderStatus.CLOSED;
    }

    public void resetTime() {
        this.requestedTime = null;
        this.approvedTime = null;
        this.canceledTime = null;
        this.startedTime = null;
        this.finishedTime = null;
        this.closedTime = null;
        this.orderStatus = OrderStatus.CREATED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return Objects.equals(createdTime, status.createdTime) && Objects.equals(approvedTime, status.approvedTime) && Objects.equals(canceledTime, status.canceledTime) && Objects.equals(startedTime, status.startedTime) && Objects.equals(finishedTime, status.finishedTime) && Objects.equals(closedTime, status.closedTime) && Objects.equals(lastChangedTime, status.lastChangedTime) && orderStatus == status.orderStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdTime, approvedTime, canceledTime, startedTime, finishedTime, closedTime, lastChangedTime, orderStatus);
    }
}