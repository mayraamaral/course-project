package dev.mayra.courses.infra.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mayra.courses.infra.config.exceptions.CustomAccessDeniedHandler;
import dev.mayra.courses.infra.config.exceptions.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final SecurityFilter securityFilter;
  private final ObjectMapper objectMapper;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .headers((headers) ->
            headers
                .frameOptions((frameOptions) -> frameOptions.disable()))
        .cors(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .exceptionHandling(exceptionHandling ->
            exceptionHandling
                .accessDeniedHandler(new CustomAccessDeniedHandler(objectMapper))
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint(objectMapper)))
        .authorizeHttpRequests((authz) -> authz
            .requestMatchers("/auth/**", "/").permitAll()
            .requestMatchers(PUT, "/user/**").hasRole("ADMIN")
            .requestMatchers(GET, "/user/**").hasRole("ADMIN")
            .requestMatchers(DELETE, "/user/**").hasRole("ADMIN")
            .requestMatchers("/course/**").hasRole("ADMIN")
            .requestMatchers(GET, "/feedback/**").hasAnyRole("ADMIN", "INSTRUCTOR")
            .requestMatchers(POST, "/feedback/**").authenticated()
            .requestMatchers(GET, "/enrollment/**").hasAnyRole("ADMIN", "INSTRUCTOR")
            .requestMatchers(POST, "/enrollment/**").authenticated()
            .requestMatchers("/report/**").hasAnyRole("ADMIN", "INSTRUCTOR")
            .requestMatchers(POST, "/user/**").permitAll()
            .anyRequest().authenticated());

    http.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().requestMatchers("/v3/api-docs",
        "/v3/api-docs/**",
        "/swagger-resources/**",
        "/swagger-ui/**");
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedMethods("*")
            .exposedHeaders("Authorization");
      }
    };
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }

}
