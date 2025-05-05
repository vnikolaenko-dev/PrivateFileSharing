package org.example.filesharing.config;

import lombok.RequiredArgsConstructor;
import org.example.filesharing.security.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

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
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Настраиваем CORS
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Разрешаем OPTIONS запросы
                        .requestMatchers("/index.html").permitAll()
                        .requestMatchers("/auth/**").permitAll() // Публичные эндпоинты
                        .requestMatchers("/users/**").hasRole("ADMIN") // Публичные эндпоинты
                        .requestMatchers("/files/**").hasAnyRole("ADMIN", "DEFAULT")
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").hasRole("ADMIN") // Документация
                )
                // Добавляем свой JWT-фильтр
                .addFilterBefore(jwtRequestFilter, AuthorizationFilter.class);
        return http.build();
    }

    /**
     * Конфигурация CORS политики.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Разрешенные источники (лучше вынести в конфиг)
        configuration.setAllowedOrigins(List.of(
                "https://localhost:8000",
                "http://localhost:8000"
        ));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Разрешенные HTTP методы
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept")); // Разрешенные заголовки
        configuration.setAllowCredentials(true); // Разрешаем передачу кук/credentials
        configuration.setMaxAge(3600L); // Время кэширования preflight запросов

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Применяем ко всем путям
        return source;
    }
}