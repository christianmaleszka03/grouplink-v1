package de.grouplink.grouplinkvaadin.security;

import com.vaadin.flow.spring.security.VaadinWebSecurity;
import de.grouplink.grouplinkvaadin.service.lowlevel.ApplicationUserLowLevelService;
import de.grouplink.grouplinkvaadin.views.login.DefaultLoginView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@SuppressWarnings("removal")
@EnableWebSecurity
@Configuration
@Slf4j
public class SecurityConfiguration extends VaadinWebSecurity {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests().requestMatchers(new AntPathRequestMatcher("/images/*.ttf")).permitAll();
        http.authorizeHttpRequests().requestMatchers(new AntPathRequestMatcher("/image/*")).permitAll();
//        http.authorizeRequests().requestMatchers(new AntPathRequestMatcher("/sitemap.xml")).permitAll();
//        http.authorizeRequests().requestMatchers(new AntPathRequestMatcher("/robots.txt")).permitAll();
//        http.addFilterBefore(new SaveRouteFilter(), FilterSecurityInterceptor.class);
        super.configure(http);
        setLoginView(http, DefaultLoginView.class);
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // ALTHOUGH THIS SEEMS LIKE USELESS CODE,
        // IT'S REQUIRED TO PREVENT SPRING BOOT AUTO-CONFIGURATION AND DEFAULT GENERATED PASSWORD
        return authenticationConfiguration.getAuthenticationManager();
    }
}
