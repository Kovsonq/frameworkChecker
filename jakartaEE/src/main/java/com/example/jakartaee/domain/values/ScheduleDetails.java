package com.example.jakartaee.domain.values;

import jakarta.enterprise.context.RequestScoped;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
public class ScheduleDetails implements Serializable {

    @NotNull
    private List<DayOfWeek> daysOfWeek;

    @FutureOrPresent
    private ZonedDateTime startDay;

    @NotNull
    @FutureOrPresent
    private ZonedDateTime endDay;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleDetails that = (ScheduleDetails) o;
        return Objects.equals(daysOfWeek, that.daysOfWeek) && Objects.equals(endDay, that.endDay)
                && Objects.equals(startDay, that.startDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(daysOfWeek, endDay, startDay);
    }
}
