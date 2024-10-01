package com.example.notes.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
                //.requestMatchers("/contact").permitAll() // Contact url ' ile biten isteği direk onayla diyoruz yani herhangi bir user password istememesini istiyoruz...
                //.requestMatchers("/public/**").permitAll() // burdada publicden sonra ne gelirse gelsin onayla izin falan isteme diyoruz.
                //.requestMatchers("/admin").denyAll() // 403 forbidden hatası aldırıyoruz yani admin endpointıne kimse giremez ...
                //.requestMatchers("/admin/**").denyAll() // ADMIN ILE BASLAYAN HER URL ICIN DENY YAPIYORUZ...
                .anyRequest().authenticated());
        //http.formLogin(withDefaults());
        http.csrf(AbstractHttpConfigurer::disable); //CSRF HATALARINI GIDERIYOR....

        /* http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));*/
        http.httpBasic(withDefaults());
        return http.build();
    }

    /*
    InMemoryUserDetailsManager: Kullanıcı bilgilerini bellekte tutan bir UserDetailsService implementasyonudur. Burada kullanıcılar veritabanı yerine bellekte saklanır.
    Yani özetle aşağıdaki metot bize user1 yoksa db de olusturmadan memory' de yapıyor yani db ' ye kaydetmiyor ...
    Ayrıca sadece get requesti atarsak admin olarak girersek admin'in attıgı  postları görebiliyoruz.Aynısı user içinde geçerli
     */
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        if(!manager.userExists("user1")){
            manager.createUser(User.withUsername("user1").password("{noop}password1").roles("USER").build()); // noop yazan yerde parolanın şifrelenmeden saklandıgını gösteriyor...
        }
        if(!manager.userExists("admin")){
            manager.createUser(User.withUsername("admin").password("{noop}adminPass").roles("ADMIN").build());
        }
        return manager;
    }

}
