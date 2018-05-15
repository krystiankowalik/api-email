package com.krystiankowalik.apiemail.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
@EnableResourceServer
public class SecurityConfig extends ResourceServerConfigurerAdapter {


    @Value("${security.authurl}")
    private String AUTH_URL;

    @Value("${security.clientId}")
    private String CLIENT_ID;

    @Value("${security.secret}")
    private String SECRET;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/secure").authenticated()
                .and()
                .authorizeRequests()
                .antMatchers("/normal", "simpleemail","email","get").permitAll();
    }

    @Bean
    public RemoteTokenServices remoteTokenServices() {
        final RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setCheckTokenEndpointUrl(AUTH_URL);
        tokenServices.setClientId(CLIENT_ID);
        tokenServices.setClientSecret(SECRET);
        return tokenServices;
    }
}
