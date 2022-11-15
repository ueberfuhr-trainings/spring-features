package de.samples.todos.boundary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableMethodSecurity
// @Profile("!no-security") - not available on native build, if we use this!
public class SecurityConfiguration {

    //@ConditionalOnBean(HttpSecurity.class) // if AutoConfiguration for Security is disabled, don't configure
    /*
    @Bean
    public SecurityFilterChain httpBasic(HttpSecurity http) throws Exception {
        http
          .authorizeHttpRequests(authz -> authz.anyRequest().authenticated())
          .httpBasic(withDefaults());
        return http.build();
    }
    */

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
