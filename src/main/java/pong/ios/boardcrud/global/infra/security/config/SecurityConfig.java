package pong.ios.boardcrud.global.infra.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import pong.ios.boardcrud.global.infra.security.jwt.filter.JwtExceptionFilter;
import pong.ios.boardcrud.global.infra.security.jwt.filter.JwtFilter;
import pong.ios.boardcrud.global.infra.security.jwt.handler.CustomAccessDeniedHandler;
import pong.ios.boardcrud.global.infra.security.jwt.handler.CustomAuthenticationEntryPoint;

import java.util.List;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtFilter jwtFilter;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"
        ));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(false);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/api/v1/auth/**").permitAll()
                        .requestMatchers("/api/v1/user/sign-up").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/boards/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/boards/**").hasAnyAuthority("ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/boards/**").hasAnyAuthority("ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/boards/**").hasAnyAuthority("ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/posts/*/reports").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/posts/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/posts/**").hasAnyAuthority("ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/posts/**").hasAnyAuthority("ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/posts/**").hasAnyAuthority("ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/posts/**").hasAnyAuthority("ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/reports/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/reports/**").hasAuthority("ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/posts/*/comments").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/comments/*/replies").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/posts/*/comments").hasAnyAuthority("ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/comments/**").hasAnyAuthority("ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/comments/**").hasAnyAuthority("ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/comments/**").hasAnyAuthority("ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/post-drafts/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v1/post-drafts/**").hasAnyAuthority("ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/post-drafts/**").hasAnyAuthority("ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN")
                        .anyRequest().authenticated()
                )

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter, JwtFilter.class)

                .exceptionHandling(ex ->
                        ex.accessDeniedHandler(customAccessDeniedHandler)
                                .authenticationEntryPoint(customAuthenticationEntryPoint)
                );

        return http.build();
    }

}
