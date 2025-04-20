package quickmath.web.security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import quickmath.Config;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/logout", "/me", "/rooms", "/rooms/**", "/ws/**").permitAll()
                        .anyRequest().authenticated()
                ).formLogin(form -> form
                        .loginProcessingUrl("/login")
                        .successHandler((req, res, auth) -> res.setStatus(HttpServletResponse.SC_OK))
                        .failureHandler((req, res, e) -> res.setStatus(HttpServletResponse.SC_UNAUTHORIZED))
                        .permitAll()
                ).logout(logout -> logout.logoutUrl("/logout").permitAll())
                .exceptionHandling(e -> e.authenticationEntryPoint((req, res, ex) -> res.setStatus(HttpServletResponse.SC_UNAUTHORIZED)).accessDeniedHandler((req, res, ex) -> ex.printStackTrace()))
                .build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(Config.FRONTEND_URL)
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("Authorization", "Content-Type", "X-XSRF-TOKEN")
                        .allowCredentials(true);
            }
        };
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User.withUsername("user1").password("{noop}password").roles("USER").build();
        UserDetails user2 = User.withUsername("user2").password("{noop}password").roles("USER").build();
        return new InMemoryUserDetailsManager(user1, user2);
    }
}
