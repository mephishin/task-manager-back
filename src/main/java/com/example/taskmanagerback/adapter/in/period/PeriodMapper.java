package com.example.taskmanagerback.adapter.in.period;

import com.example.taskmanagerback.adapter.in.period.dto.PeriodDto;
import com.example.taskmanagerback.model.period.Period;
import com.example.taskmanagerback.util.DateTimeUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class PeriodMapper {
    @Mapping(target = "project", source = "period.project.name")
    @Mapping(target = "started", qualifiedByName = "getFormattedInstant", source = "started")
    @Mapping(target = "ended", qualifiedByName = "getFormattedInstant", source = "ended")
    public abstract PeriodDto periodToPeriodDto(Period period);

    @Named("getFormattedInstant")
    protected String getFormattedInstant(Instant instant) {
        return Optional.ofNullable(instant)
                .map(PeriodMapper::formatInstant)
                .orElse(null);
    }

    private static String formatInstant(Instant value) {
        return DateTimeUtils.DATE_TIME_FORMAT.format(value.atZone(ZoneId.of("Europe/Moscow")));
    }
}
