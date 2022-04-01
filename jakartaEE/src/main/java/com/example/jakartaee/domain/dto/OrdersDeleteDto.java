package com.example.jakartaee.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class OrdersDeleteDto {

    private String startDate;

    private String endDate;

    private String scheduleId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdersDeleteDto that = (OrdersDeleteDto) o;
        return Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(scheduleId, that.scheduleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, scheduleId);
    }
}
