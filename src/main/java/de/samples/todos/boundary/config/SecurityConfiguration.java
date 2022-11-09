package de.samples.todos.boundary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    // https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
    @Bean
    @Profile("!security")
    public SecurityFilterChain httpBasic(HttpSecurity http) throws Exception {
        http
          .authorizeHttpRequests(authz -> authz.anyRequest().authenticated())
          .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    @Profile("!security")
    public SecurityFilterChain noSecurity(HttpSecurity http) throws Exception {
        return http
          .authorizeHttpRequests(authz -> authz.anyRequest().permitAll())
          .build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        return new InMemoryUserDetailsManager(
          User
            .withUsername("user")
            .password(passwordEncoder.encode("password"))
            .roles("USER")
            .build(),
          User
            .withUsername("admin")
            .password(passwordEncoder.encode("password"))
            .roles("ADMIN")
            .build()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
