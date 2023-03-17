package com.patandmat.otmz.domain.member.config;

import com.patandmat.otmz.domain.member.interceptor.TokenCheckInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;
// @MapperScan(basePackages = { "com.ssafy.**.mapper" })
@Configuration
@EnableAspectJAutoProxy
@EnableWebSecurity
public class AccessConfig implements WebMvcConfigurer {
    private final List<String> pat = Arrays.asList("/oauth/**","/oauthredirect/**","/user/login/**","/user/join/**","/user/refresh","/swagger-ui/index.html","/swagger-ui/**","/user/findpassword/**","/v3/api-docs/**","/api-docs/**","/user/findpassword","/user/getimgpaths","/user/getphotoandtitle","/user/getphotocomment","/api/throw/**","/diary");
    @Autowired
    private TokenCheckInterceptor tokenCheckInterceptor;
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*")
                .allowedOrigins("http://localhost:80", "http://localhost:8080","http://127.0.0.1:5500", "http://localhost:8081","https://i8c108.p.ssafy.io")
                .allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(),
                        HttpMethod.DELETE.name(), HttpMethod.HEAD.name(), HttpMethod.OPTIONS.name(),
                        HttpMethod.PATCH.name())
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD")
                .maxAge(1800);
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenCheckInterceptor).addPathPatterns("/**").excludePathPatterns(pat);
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.withUsername("user")
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //http.cors().and().csrf().disable();
        http.csrf().disable();
//      http.authorizeHttpRequests().requestMatchers("/**");
//      http.authorizeRequests()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic().disable();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder;
    }
}


