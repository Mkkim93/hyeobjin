package com.hyeobjin.config.security;

import com.hyeobjin.application.common.service.redis.RedisService;
import com.hyeobjin.jwt.CustomLogoutFilter;
import com.hyeobjin.jwt.JwtFilter;
import com.hyeobjin.jwt.JwtUtil;
import com.hyeobjin.jwt.LoginFilter;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.security.web.authentication.logout.LogoutFilter;
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
                        .requestMatchers("/auth", "/boards/**", "/boardFiles/**",
                                "/calendar/**", "/files/**", "/inquiry/**",
                                "/manufacturers/**","/items/**", "/type/**", "/error",
                                "/swagger-ui/**", "/v3/api-docs/**",  "/swagger-ui.html/**",
                                "/swagger-resources/**", "/webjars/**", "/image/**",
                                "/v3/api-docs/swagger-config", "/login").permitAll()

                        .requestMatchers("/admins", "/admin/inquiry/**", "/admin/items/**", "/admin/calendar/**",
                                "/admin/boards/**", "/admin/info/**"
                                ).hasAnyRole("USER", "ADMIN")

                        .requestMatchers("/admin/users/**", "/admin/type/**", "/admin/glass/**").hasRole("ADMIN")
                        .anyRequest().authenticated());

        http
                .addFilterBefore(new CustomLogoutFilter(jwtUtil, redisService), LogoutFilter.class);

        http
                .addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class);

        http
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration),
                                jwtUtil, redisService),
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
                "https://db63-125-186-22-14.ngrok-free.app/"
        ));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PATCH", "OPTIONS"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",  configuration);
        return source;
    }
}
