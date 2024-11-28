package com.upiiz.pyments.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Permite usar @PreAuthorize en métodos
public class SecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    // Configuración de la cadena de filtros de seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(Customizer.withDefaults())
                .csrf().disable() // Deshabilitar CSRF para facilitar pruebas (habilítalo en producción)
                .httpBasic(Customizer.withDefaults()) // Autenticación básica
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless para APIs REST
                .authorizeHttpRequests(auth -> {
                    // Configuración de permisos por endpoint
                    auth.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").hasAnyAuthority("READ");
                    auth.requestMatchers(HttpMethod.GET, "/api/v1/pyments/**").hasAuthority("READ");
                    auth.requestMatchers(HttpMethod.POST, "/api/v1/pyments/**").hasAuthority("CREATE");
                    auth.requestMatchers(HttpMethod.PUT, "/api/v1/pyments/**").hasAuthority("UPDATE");
                    auth.requestMatchers(HttpMethod.DELETE, "/api/v1/pyments/**").hasAuthority("DELETE");
                    // Permitir acceso al resto de los endpoints solo a usuarios autenticados
                    auth.anyRequest().authenticated();
                })
                .build();
    }

    // Authentication Manager - Maneja la autenticación con el proveedor configurado
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Authentication Provider - Configura cómo se autentican los usuarios
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder()); // Codificador de contraseñas
        daoAuthenticationProvider.setUserDetailsService(userDetailsService()); // Servicio de detalles del usuario
        return daoAuthenticationProvider;
    }

    // Configuración del codificador de contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // Solo para pruebas; usa BCrypt en producción
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
}
