package com.blog.blog_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import com.blog.blog_server.utils.OffsetDateTimeReadConverter;
import com.blog.blog_server.utils.OffsetDateTimeWriteConverter;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Optional;

@Configuration
@EnableMongoRepositories("com.blog.blog_server.repository")
@EnableMongoAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
public class MongoConfig {
    @Bean(name="auditingDateTimeProvider")
    public DateTimeProvider dateTimeProvider() {
        return () -> Optional.of(OffsetDateTime.now());
    }

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(Arrays.asList(
                new OffsetDateTimeWriteConverter(),
                new OffsetDateTimeReadConverter()
        ));
    }
}
