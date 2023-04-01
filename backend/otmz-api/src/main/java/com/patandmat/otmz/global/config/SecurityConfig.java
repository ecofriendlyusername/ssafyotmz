package com.patandmat.otmz.global.config;

import com.patandmat.otmz.global.auth.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final WebConfig webMvcConfig;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().configurationSource(webMvcConfig.corsConfigurationSource())
            .and()
            .httpBasic().disable()
            .authorizeHttpRequests((authorize) ->
                    authorize.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                             .requestMatchers(AntPathRequestMatcher.antMatcher("/swagger-ui/**")).permitAll()
                             .requestMatchers(AntPathRequestMatcher.antMatcher("/v3/api-docs/**")).permitAll()
                             .requestMatchers(AntPathRequestMatcher.antMatcher("/oauth/**")).permitAll()
                             .requestMatchers(AntPathRequestMatcher.antMatcher("/images/**")).permitAll()
                             .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/item/**")).permitAll()
                             .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/items/**")).permitAll()
                             .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/items/brandi")).permitAll()
                             .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .formLogin().disable()
            .logout().disable()
            .headers(headers -> headers.frameOptions().disable())
            .csrf().disable()
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
