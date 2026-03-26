package com.smartwallet.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.bson.Document;

@Configuration
public class MongoConfig {

    @Bean
    public CommandLineRunner mongoStatus(MongoTemplate mongoTemplate) {
        return args -> {
            try {
                System.out.println("🔍 Running MongoDB Ping Test...");
                Document ping = new Document("ping", 1);
                mongoTemplate.getDb().runCommand(ping);
                System.out.println("✅ MongoDB Connected & Verified (Ping OK): " + 
                                   mongoTemplate.getDb().getName());
            } catch (Exception e) {
                System.out.println("❌ MongoDB Connection/Authentication Failed!");
                System.out.println("⚠️ Error details: " + e.getMessage());
                if (e.getMessage().contains("authentication failed")) {
                    System.out.println("💡 TIP: Check if authSource=admin is needed or if credentials for '" + 
                                       mongoTemplate.getDb().getName() + "' are correct.");
                }
            }
        };
    }
}
