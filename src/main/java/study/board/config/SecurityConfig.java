package study.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final String[] AUTH_WHITELIST = {
            "/","/api/**"
    };

    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception{
        return http
                .csrf().disable()
                .authorizeHttpRequests(authorize -> authorize
                    .shouldFilterAllDispatcherTypes(false)
                    .requestMatchers(AUTH_WHITELIST)
                    .permitAll()
                    .anyRequest()
                    .authenticated())
                .build();
    }
}
