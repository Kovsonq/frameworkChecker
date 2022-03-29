package com.example.jakartaee.domain.dto;

import com.example.jakartaee.domain.values.ScheduleDetails;
import jakarta.enterprise.context.RequestScoped;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
public class OrdersDetailsDto {

    @FutureOrPresent
    @NotNull
    private ZonedDateTime startDate;

    @NotNull
    @Max(480)
    private Long duration;

    @Max(480)
    private Long pauseDuration;

    @Max(100)
    private Integer quantity;

    private ScheduleDetails scheduleDetails;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdersDetailsDto that = (OrdersDetailsDto) o;
        return Objects.equals(startDate, that.startDate) && Objects.equals(duration, that.duration) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(scheduleDetails, that.scheduleDetails) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, duration, quantity, scheduleDetails);
    }

}
