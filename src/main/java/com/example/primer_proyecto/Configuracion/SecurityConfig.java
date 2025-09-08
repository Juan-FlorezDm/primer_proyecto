package com.example.primer_proyecto.Configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

     @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/registro", "/insertarusuario", "/iniciarSesion", "/css/**", "/js/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/iniciarSesion")
                .loginProcessingUrl("/iniciarSesion")
                .defaultSuccessUrl("/", true)   // 👈 después del login redirige a index
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")          // URL para cerrar sesión
                .logoutSuccessUrl("/iniciarSesion?logout") // redirige tras cerrar sesión
                .permitAll()
            )
            .rememberMe(remember -> remember
                .key("uniqueAndSecret")   // clave única para firmar la cookie
                .tokenValiditySeconds(86400) // 1 día
            );


        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(JdbcTemplate jdbcTemplate) {
        return username -> jdbcTemplate.queryForObject(
            "SELECT email, contrasena FROM usuarios WHERE email = ?",
            (rs, rowNum) -> {
                String correo = rs.getString("email");
                String password = rs.getString("contrasena");
                return User.withUsername(correo)
                        .password(password) // ya está cifrada en la BD
                        .roles("USER")
                        .build();
            },
            username
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

