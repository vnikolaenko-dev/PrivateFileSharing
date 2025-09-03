package org.example.microservices.config;

import lombok.RequiredArgsConstructor;
import org.example.microservices.security.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

/**
 * Конфигурация безопасности Spring Security.
 * Настраивает аутентификацию, авторизацию и CORS политику.
 */
@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity // Включает аннотационную безопасность (@PreAuthorize и др.)
public class SecurityConfig {
    private final JwtRequestFilter jwtRequestFilter; // Фильтр для JWT аутентификации

    /**
     * Конфигурация цепочки фильтров безопасности.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Отключаем стандартную форму входа
                .formLogin(form -> form.disable())

                // Отключаем базовую HTTP-аутентификацию
                .httpBasic(basic -> basic.disable())

                // Делаем приложение STATELESS (для JWT)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .csrf(AbstractHttpConfigurer::disable) // Отключаем CSRF, так как используем JWT
                .cors(cors -> cors.disable())

                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Разрешаем OPTIONS запросы
                        .requestMatchers("/auth/**").permitAll() // Публичные эндпоинты
                        .requestMatchers("/user/**").hasRole("ADMIN") // Публичные эндпоинты
                        .requestMatchers("/files/**").hasAnyRole("ADMIN", "DEFAULT")
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").hasRole("ADMIN") // Документация
                )
                // Добавляем свой JWT-фильтр
                .addFilterBefore(jwtRequestFilter, AuthorizationFilter.class);
        return http.build();
    }
}