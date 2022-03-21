package com.example.jakartaee.domain;

import com.example.jakartaee.domain.values.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "status")
@Getter
@ToString
@NoArgsConstructor
public class Status implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name="createdTime", updatable = false)
    private ZonedDateTime createdTime;
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

    @OneToOne
    private Order order;

    public Status(Order order) {
        this.createdTime = ZonedDateTime.now();
        this.lastChangedTime = ZonedDateTime.now();
        this.orderStatus = OrderStatus.CREATED;
        this.order = order;
    }

    public void setApprovedTime() {
        this.approvedTime = ZonedDateTime.now();
        this.lastChangedTime = ZonedDateTime.now();
        this.orderStatus = OrderStatus.APPROVED;
    }

    public void setCanceledTime() {
        this.canceledTime = ZonedDateTime.now();
        this.lastChangedTime = ZonedDateTime.now();
        this.orderStatus = OrderStatus.CANCELED;
    }

    public void setStartedTime() {
        this.startedTime = ZonedDateTime.now();
        this.lastChangedTime = ZonedDateTime.now();
        this.orderStatus = OrderStatus.STARTED;
    }

    public void setFinishedTime() {
        this.finishedTime = ZonedDateTime.now();
        this.lastChangedTime = ZonedDateTime.now();
        this.orderStatus = OrderStatus.FINISHED;
    }

    public void setClosedTime() {
        this.closedTime = ZonedDateTime.now();
        this.lastChangedTime = ZonedDateTime.now();
        this.orderStatus = OrderStatus.CLOSED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Status status = (Status) o;
        return Objects.equals(createdTime, status.createdTime) && Objects.equals(approvedTime, status.approvedTime) && Objects.equals(startedTime, status.startedTime) && Objects.equals(finishedTime, status.finishedTime) && Objects.equals(closedTime, status.closedTime) && Objects.equals(lastChangedTime, status.lastChangedTime) && orderStatus == status.orderStatus && Objects.equals(order, status.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdTime, approvedTime, startedTime, finishedTime, closedTime, lastChangedTime, orderStatus, order);
    }

}