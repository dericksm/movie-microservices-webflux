package com.github.dericksm.moviesinfoservice.config;

import com.github.dericksm.moviesinfoservice.infrastructure.converter.OffsetDateTimeReadConverter;
import com.github.dericksm.moviesinfoservice.infrastructure.converter.OffsetDateTimeWriteConverter;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

@Configuration
public class MongoConfig {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Arrays.asList(
            new OffsetDateTimeReadConverter(),
            new OffsetDateTimeWriteConverter()
        ));
    }

}
 