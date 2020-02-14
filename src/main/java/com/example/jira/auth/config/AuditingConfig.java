package com.example.jira.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing //pour activer l'audit JPA
public class AuditingConfig {
    // That's all here for now. We'll add more auditing configurations later.
}