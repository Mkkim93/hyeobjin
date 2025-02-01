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
                // 중요!! : Spring Security 가 로그인 처리를 담당할 url, 프론트의 비동기 처리 할 url 과 매핑 (컨트롤러 필요없음)
                // 여기서 설정하는 url 경로는 클라이언트의 웹사이트의 경로가 아니라 서버 restcontroller 의 rest api 경로 기준이다
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login","/auth","/items/**",
                                "/manufacturers/**", "/boards/**", "/item/**",
                                "/swagger-ui").permitAll()

                        .requestMatchers("/admins", "/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated());

        http
                .addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class);

        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, redisService),
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
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With")); // 허용할 헤더
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setAllowCredentials(true); // 쿠키 허용 여부
        configuration.setMaxAge(3600L);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "PATCH"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",  configuration); // 모든 경로에 대해 CORS 설정 적용
        return source;
    }
}
