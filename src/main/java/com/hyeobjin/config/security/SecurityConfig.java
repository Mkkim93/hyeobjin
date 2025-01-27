package com.hyeobjin.config.security;

import com.hyeobjin.application.common.service.redis.RedisService;
import com.hyeobjin.jwt.JwtFilter;
import com.hyeobjin.jwt.JwtUtil;
import com.hyeobjin.jwt.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Security & cors Config
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final RedisService redisService;
    private final JwtUtil jwtUtil;
    private final AuthenticationConfiguration authenticationConfiguration;

    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JwtUtil jwtUtil, RedisService redisService) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtUtil = jwtUtil;
        this.redisService = redisService;
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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf((auth) -> auth.disable())
                        .cors((cors) -> cors
                                .configurationSource(corsConfigurationSource()));

        http
                .formLogin((auth) -> auth.disable());

        http
                .httpBasic((auth) -> auth.disable());

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login", "/auth","/items",
                                "/notice", "/**", "/swagger-ui").permitAll() // 중요!! : Spring Security 가 로그인 처리를 담당할 url, 프론트의 비동기 처리 할 url 과 매핑 (컨트롤러 필요없음)

                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated());

        // TODO JwtFilter 로직 실행 안됨 나중에 확인
        http
                .addFilterAt(new JwtFilter(jwtUtil), LoginFilter.class);

        http
                .addFilterBefore(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, redisService),
                        UsernamePasswordAuthenticationFilter.class);

        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",
                "https://d92b-118-217-209-89.ngrok-free.app" // ngrok 임시 활성화
        ));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // 허용할 헤더
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setAllowCredentials(true); // 쿠키 허용 여부
        configuration.setMaxAge(3600L);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "PATCH"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",  configuration); // 모든 경로에 대해 CORS 설정 적용
        return source;
    }
}
