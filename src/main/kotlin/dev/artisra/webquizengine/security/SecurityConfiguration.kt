package dev.artisra.webquizengine.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@Configuration
class SecurityConfiguration {
    @Bean
    fun getSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/h2-console/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/register").permitAll()
                    .requestMatchers(HttpMethod.POST, "/actuator/shutdown").permitAll()
                    .requestMatchers("/api/quizzes/**").authenticated()
                    .anyRequest().denyAll()
            }
            .csrf { it.disable() }
            .headers { headers ->
                headers.frameOptions { it.sameOrigin() } // Allow frames for the H2 console
            }
            .httpBasic(Customizer.withDefaults())
        return http.build()
    }

    @Bean
    fun getBCryptPasswordEncoder(): PasswordEncoder {
        val strength = 7
        return BCryptPasswordEncoder(strength)
    }
}
