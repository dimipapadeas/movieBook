package org.papadeas.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.papadeas.filters.JwtFilter;
import org.papadeas.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@PropertySource("classpath:jwt.properties")
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

  private final JwtFilter jwtFilter;

  private final UserService userService;


  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }


  @Bean
  public AuthenticationManager authManager(HttpSecurity http)
      throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
        .userDetailsService(userService)
        .passwordEncoder(getPasswordEncoder())
        .and()
        .build();
  }


  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    //@formatter:off
    http.cors()
        .and().csrf().disable()
        .authorizeRequests()
        .antMatchers("/static/**").permitAll()
        .antMatchers("/resources/**").permitAll()
        .antMatchers("/*").permitAll()
        .antMatchers("/api/authenticate").permitAll()
        .antMatchers("/api/user/**").permitAll()
        .antMatchers("/api/movie/all").permitAll()
        .antMatchers("/api/movie/users").permitAll();
    http.csrf().disable()
        .cors().configurationSource(request -> {
          var cors = new CorsConfiguration();
          cors.setAllowedOrigins(List.of("http://localhost:8080", "http://127.0.0.1:80"));
          cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
          cors.setAllowedHeaders(List.of("*"));
          return cors;
        })
        .and().authorizeRequests()
        .anyRequest().authenticated()
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    //@formatter:on
    return http.build();
  }
}
