package com.github.dericksm.moviesinfoservice.infrastructure.converter;

import java.time.OffsetDateTime;
import java.util.Date;
import org.springframework.core.convert.converter.Converter;

public class OffsetDateTimeWriteConverter implements Converter<OffsetDateTime, Date> {

    @Override
    public Date convert(OffsetDateTime source) {
        return Date.from(source.toInstant());
    }
}
