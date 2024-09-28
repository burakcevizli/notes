package com.example.notes.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // /public/login
    // /public/signup

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/contact").permitAll() // Contact url ' ile biten isteği direk onayla diyoruz yani herhangi bir user password istememesini istiyoruz...
                .requestMatchers("/public/**").permitAll() // burdada publicden sonra ne gelirse gelsin onayla izin falan isteme diyoruz.
                .requestMatchers("/admin").denyAll() // 403 forbidden hatası aldırıyoruz yani admin endpointıne kimse giremez ...
                .requestMatchers("/admin/**").denyAll() // ADMIN ILE BASLAYAN HER URL ICIN DENY YAPIYORUZ...
                .anyRequest().authenticated());
        //http.formLogin(withDefaults());
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.httpBasic(withDefaults());
        return http.build();
    }
}
