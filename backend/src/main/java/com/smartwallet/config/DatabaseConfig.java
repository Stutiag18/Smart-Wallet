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
            return findDatabaseUrl() != null || 
                   (System.getenv("PGHOST") != null && System.getenv("PGDATABASE") != null);
        }
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        String url = findDatabaseUrl();
        String user = System.getenv("PGUSER");
        String pass = System.getenv("PGPASSWORD");

        if (url != null) {
            try {
                URI dbUri = new URI(url);
                user = dbUri.getUserInfo().split(":")[0];
                pass = dbUri.getUserInfo().split(":")[1];
                url = "jdbc:postgresql://" + dbUri.getHost() + ":" + dbUri.getPort() + dbUri.getPath();
                System.out.println("🚀 DatabaseConfig: Discovered via URL pattern");
            } catch (Exception e) {
                // Ignore and try separate vars
                url = null;
            }
        }

        if (url == null) {
            String host = System.getenv("PGHOST");
            String port = System.getenv("PGPORT");
            String db = System.getenv("PGDATABASE");
            url = String.format("jdbc:postgresql://%s:%s/%s", host, port != null ? port : "5432", db);
            System.out.println("🚀 DatabaseConfig: Discovered via PGHOST pattern");
        }

        return DataSourceBuilder.create()
                .url(url)
                .username(user != null ? user : "postgres")
                .password(pass != null ? pass : "postgres")
                .driverClassName("org.postgresql.Driver")
                .build();
    }

    private static String findDatabaseUrl() {
        return System.getenv().values().stream()
                .filter(v -> v != null && (v.startsWith("postgres://") || v.startsWith("jdbc:postgresql://")))
                .findFirst()
                .orElse(null);
    }
}
