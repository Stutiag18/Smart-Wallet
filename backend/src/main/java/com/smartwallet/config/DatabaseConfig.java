package com.smartwallet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.boot.jdbc.DataSourceBuilder;
import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

@Configuration
@Conditional(DatabaseConfig.PostgresCondition.class)
public class DatabaseConfig {

    public static class PostgresCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return System.getenv().values().stream()
                    .anyMatch(v -> v != null && v.startsWith("postgres://"));
        }
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        String databaseUrl = findEnvironmentVariable("postgres://");
        
        try {
            URI dbUri = new URI(databaseUrl);
            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + dbUri.getPort() + dbUri.getPath();

            System.out.println("🚀 DatabaseConfig: Auto-discovered remote database at " + dbUri.getHost());

            return DataSourceBuilder.create()
                    .url(dbUrl)
                    .username(username)
                    .password(password)
                    .driverClassName("org.postgresql.Driver")
                    .build();
        } catch (Exception e) {
            return null;
        }
    }

    private String findEnvironmentVariable(String prefix) {
        return System.getenv().values().stream()
                .filter(v -> v != null && v.startsWith(prefix))
                .findFirst()
                .orElse(null);
    }
}
