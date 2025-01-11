package com.upiiz.pyments.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Permite usar @PreAuthorize en métodos
public class SecurityConfig {

    // Configuración de la cadena de filtros de seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().authenticated()
                )
                .httpBasic();

        return http.build();
    }

    // Configuración de usuarios en memoria (para pruebas)
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails usuarioAdmin = User.withUsername("Guillermo")
                .password(passwordEncoder().encode("memo1234"))
                .roles("ADMIN")
                .authorities("READ", "CREATE", "UPDATE", "DELETE")
                .build();

        UserDetails usuarioUsuario = User.withUsername("User")
                .password(passwordEncoder().encode("user1234"))
                .roles("USER")
                .authorities("READ")
                .build();

        UserDetails usuarioModerator = User.withUsername("Moderator")
                .password(passwordEncoder().encode("moderator1234"))
                .roles("MODERATOR")
                .authorities("READ", "UPDATE")
                .build();

        UserDetails usuarioEditor = User.withUsername("Editor")
                .password(passwordEncoder().encode("editor1234"))
                .roles("EDITOR")
                .authorities("UPDATE")
                .build();

        UserDetails usuarioDeveloper = User.withUsername("Developer")
                .password(passwordEncoder().encode("developer1234"))
                .roles("DEVELOPER")
                .authorities("READ", "CREATE", "UPDATE", "DELETE")
                .build();

        UserDetails usuarioAnalyst = User.withUsername("Analyst")
                .password(passwordEncoder().encode("analyst1234"))
                .roles("ANALYST")
                .authorities("READ", "DELETE")
                .build();

        return new InMemoryUserDetailsManager(usuarioAdmin, usuarioUsuario, usuarioModerator, usuarioEditor, usuarioDeveloper, usuarioAnalyst);
    }

     //CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // Configuración del codificador de contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // Solo para pruebas; usa BCrypt en producción
    }
}
