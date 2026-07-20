package com.example.SpringDataJpaDemo.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){
    httpSecurity.csrf(csrf->csrf.disable())
            .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth->
                    auth.requestMatchers("/api/v1/auth/**").permitAll()
                            .requestMatchers(HttpMethod.POST,"/api/v1/users").permitAll()
                            .requestMatchers("/api/v1/users").hasRole("ADMIN")
                            .requestMatchers("/api/v1/users/*/orders/**").hasAnyRole("ADMIN","USER")
                            .anyRequest().authenticated());
//            .httpBasic(Customizer.withDefaults());
    return httpSecurity.build();

    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//    @Bean
//    UserDetailsManager userDetailsManager(PasswordEncoder passwordEncoder){
//        UserDetails admin= User.withUsername("ADMIN")
//                .roles("ADMIN")
//                .password(passwordEncoder.encode("pass123"))
//                .build();t
//        UserDetails user=User.withUsername("USER")
//                .roles("USER")
//                .password(passwordEncoder.encode("user123"))
//                .build();
//        return new InMemoryUserDetailsManager(user,admin);
//    }
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig){
        return authConfig.getAuthenticationManager();
    }


}
