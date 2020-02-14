package com.example.jira.auth.security;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal //pour accéder à l'utilisateur actuellement authentifié dans les contrôleurs.
public @interface CurrentUser {

}