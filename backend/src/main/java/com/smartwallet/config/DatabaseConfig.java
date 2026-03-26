package com.smartwallet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.boot.jdbc.DataSourceBuilder;
import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class DatabaseConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        // Auto-discover any variable starting with postgres://
        String databaseUrl = System.getenv().values().stream()
                .filter(v -> v != null && v.startsWith("postgres://"))
                .findFirst()
                .orElse(null);
        
        if (databaseUrl == null) {
            // Fallback for local development
            return null;
        }

        try {
            // Railway/Heroku format: postgres://user:pass@host:port/dbname
            URI dbUri = new URI(databaseUrl);

            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + dbUri.getPort() + dbUri.getPath();

            return DataSourceBuilder.create()
                    .url(dbUrl)
                    .username(username)
                    .password(password)
                    .driverClassName("org.postgresql.Driver")
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException("❌ Failed to parse DATABASE_URL: " + databaseUrl, e);
        }
    }
}
