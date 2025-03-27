package com.wilsonfung.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.InMemoryRelyingPartyRegistrationRepository;
import org.springframework.security.saml2.provider.service.registration.RelyingPartyRegistration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // .authorizeRequests(authorize -> authorize
            //     .antMatchers("/api/log-messages").authenticated()
            //     .anyRequest().permitAll()
            // )
            // .saml2Login(saml2 -> saml2
            //     .loginPage("/login")
            // )
            // .csrf(csrf -> csrf.disable());
            .authorizeRequests().anyRequest().permitAll();
            
        return http.build();
    }

    // @Bean
    // public RelyingPartyRegistrationRepository relyingPartyRegistrationRepository() {
    //     RelyingPartyRegistration registration = RelyingPartyRegistration
    //         .withRegistrationId("default")
    //         .assertingPartyDetails(party -> party
    //             .entityId("http://localhost:8080/saml/metadata")
    //             .singleSignOnServiceLocation("http://localhost:8080/saml/SSO")
    //             .wantAuthnRequestsSigned(false)
    //         )
    //         .build();
    //     return new InMemoryRelyingPartyRegistrationRepository(registration);
    // }
}
