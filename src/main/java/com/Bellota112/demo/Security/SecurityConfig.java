package com.Bellota112.demo.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Rutas públicas
                .requestMatchers("/login", "/recuperaContrasenias", "/css/**", "/js/**", "/images/**").permitAll()
                // Proteger cualquier otra solicitud
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") // Página de inicio de sesión personalizada
                .defaultSuccessUrl("/incidencias", true) // Redirección después de inicio de sesión exitoso
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout") // Redirección después del cierre de sesión
                .permitAll()
            )
            .csrf().disable(); // Deshabilitar CSRF para pruebas; habilitarlo en producción
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}