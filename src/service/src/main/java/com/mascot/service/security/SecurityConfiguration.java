package com.mascot.service.security;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import java.util.ArrayList;

/**
 * Created by Nikolay on 08.12.2015.
 */
//@Configuration
@EnableWebSecurity
//@Order(  SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(/*securedEnabled = true, */prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
        Logger.getLogger(getClass()).info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" + auth.toString());
        auth.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//                final byte[] encode = Base64.encode("2:1".getBytes());
//                final String password = new String(encode);
                final ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                return new User(username, "1"/*password*/, authorities);
            }
        });

/*
        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
*/
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        Logger.getLogger(getClass()).info("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
        http.
                authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/api/authenticate").permitAll()
//                    .antMatchers(HttpMethod.GET, "/api/users").permitAll()
//                    .antMatchers(HttpMethod.GET, "/api/users/*").permitAll()
                .and()
                .csrf().disable()
                .authorizeRequests()
//                    .anyRequest().authenticated()
                    .and()
                .httpBasic();

/*
        http
//                .httpBasic().and()
//                .authorizeRequests()
//                .antMatchers("/index.html", "/home.html", "/login.html", "/").permitAll().anyRequest()
//                .authenticated().and()
                .addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class).
                csrf().csrfTokenRepository(csrfTokenRepository());
*/
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }
}
