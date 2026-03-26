package com.smartwallet.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.boot.jdbc.DataSourceBuilder;
import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

@Configuration
@ConditionalOnProperty(name = "DATABASE_URL")
public class DatabaseConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        String databaseUrl = System.getenv("DATABASE_URL");
        
        // This method will only be called if DATABASE_URL is present
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
