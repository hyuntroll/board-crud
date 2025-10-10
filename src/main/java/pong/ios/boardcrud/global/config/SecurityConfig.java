package pong.ios.boardcrud.global.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import pong.ios.boardcrud.global.auth.security.jwt.*;
import pong.ios.boardcrud.security.jwt.*;
import pong.ios.boardcrud.global.service.RedisService;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    private final JwtUtil jwtUtil;

    private final RedisService redisService;

    private final JwtTokenProvider jwtTokenProvider;


    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
//        Deprecated::
//        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
//
//        hierarchy.setHierarchy(
//                        "ROLE_ADMIN > ROLE_TEACHER\n"+
//                        "ROLE_TEACHER > ROLE_USER\n");
//        return hierarchy;

        return RoleHierarchyImpl.fromHierarchy("""
               ROLE_ADMIN > ROLE_TEACHER
               ROLE_TEACHER > ROLE_USER
               """);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtTokenProvider jwtTokenProvider) throws Exception {

        http
                .cors((cors)-> cors
                        .configurationSource(new CorsConfigurationSource() {

                            @Override
                            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                                CorsConfiguration configuration = new CorsConfiguration();

                                configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3001"));
                                configuration.setAllowedMethods(Collections.singletonList("*"));
                                configuration.setAllowCredentials(true);
                                configuration.setAllowedHeaders(Collections.singletonList("*"));
                                configuration.setMaxAge(3600L);

                                configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                                return configuration;
                            }
                        }));

        //csrf disable
        http
                .csrf((auth) -> auth.disable()); // STATELESS상태로 관리하기 때문에 공격을 방어하지 않아도 됨
        //From 로그인 방식 disable
        http
                .formLogin((auth) -> auth.disable());
        //http basic 인증 방식 disable
        http
                .httpBasic((auth) -> auth.disable());

        // 경로별 인가 작업
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login", "/logout", "/user/join", "/swagger-ui/**", "/v3/**").permitAll()
                        .requestMatchers("/reissue").permitAll()


                        .requestMatchers(HttpMethod.GET, "/post").permitAll()
                        .requestMatchers(HttpMethod.GET, "/post/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/post").hasRole("USER")
                        .requestMatchers(HttpMethod.PATCH, "/post/**").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/post/**").hasRole("USER")


                        .requestMatchers(HttpMethod.POST, "/comment/*").hasRole("USER")
                        .requestMatchers(HttpMethod.PATCH, "/comment").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/comment").hasRole("USER")

                        .requestMatchers(HttpMethod.POST, "/like/*").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/like/*").hasRole("USER")


                        .requestMatchers(HttpMethod.GET, "/user/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/user").permitAll()
                        .requestMatchers(HttpMethod.POST, "/user/**").hasRole("USER")
                        .requestMatchers(HttpMethod.PATCH, "/user/**").hasRole("USER")
                        .requestMatchers(HttpMethod.DELETE, "/user/**").hasRole("USER")


                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/error").permitAll()
                        .anyRequest().authenticated());

        // JWTFilter 등록
        http
                .addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class);
        // 필터 등록
        http
                .addFilterAt(new JwtLoginFilter(authenticationManager(authenticationConfiguration), jwtTokenProvider, redisService, objectMapper(), jwtUtil), UsernamePasswordAuthenticationFilter.class);
        http
                .addFilterBefore(new CustomLogoutFilter(jwtUtil, redisService), LogoutFilter.class);
        //세션 설정
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

}
