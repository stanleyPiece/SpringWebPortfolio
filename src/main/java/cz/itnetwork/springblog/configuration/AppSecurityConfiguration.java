package cz.itnetwork.springblog.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


//konfigurační třída pro nastavení přístupů
@Configuration
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true) // "aktivace" anotace @Secured
public class AppSecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests() //slouží k označení stránek, které budou dostupné přihlášeným uživatelům
                        .anyRequest()
                        .permitAll() // <-- Všechny stránky povolíme (pravidla budeme definovat přímo v kontroleru)
                        .and()
                        .formLogin()
                            .loginPage("/account/login") // Přihlašovací URL adresa
                            .loginProcessingUrl("/account/login") // Přihlašovací URL adresa
                            .defaultSuccessUrl("/articles", true) // Nastavení přesměrování po úspěšném přihlášení
                            .usernameParameter("email") // Chceme se přihlašovat pomocí emailu
                            .permitAll() // Povolit vstup na `/account/login` i nepřihlášeným uživatelům
                        .and()
                        .logout()
                        .logoutUrl("/account/logout") // Odhlašovací URL adresa
                .and()
                .build();
    }

    @Bean //metoda pro hašování uživatelských hesel – vznikne otisk, který se uloží do databáze
          //                                        - následně tento otisk budeme porovnávat s hodnotou (heslem), které uživatel zadal při registraci a zadává při přihlášení
    public PasswordEncoder passwordEncoder() { //PasswordEncoder = hašovací rozhraní
        return new BCryptPasswordEncoder(); // třída představující hašovací algoritmus bcrypt ve SpringBoot
                                            // druhý (modernější) algoritmus je Argon2
    }
}
