package com.mascot.service.security;

import com.mascot.common.MascotAppContext;
import com.mascot.server.beans.UserService;
import com.mascot.server.model.Role;
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
import java.util.List;

/**
 * Created by Nikolay on 08.12.2015.
 */
//@Configuration
@EnableWebSecurity
//@Order(  SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(/*securedEnabled = true, */prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//                final byte[] encode = Base64.encode("2:1".getBytes());
//                final String password = new String(encode);
                final UserService userService = MascotAppContext.getBean(UserService.class);
                final com.mascot.server.model.User appUser = userService.loadUserByLogin(username);
                if (appUser == null) {
                    final String msg = "Not found user with login '" + username + "'";
                    logger.info(msg);
                    throw new UsernameNotFoundException(msg);
                }
                final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                for (Role role : appUser.getRoles()) {
                    authorities.add(new SimpleGrantedAuthority(role.getName()));
                }
                final byte[] encode = Base64.decode(appUser.getPassword().getBytes());
//                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                return new User(username, /*"1"*/new String(encode), authorities);
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
        http.
                authorizeRequests()
                    .antMatchers(HttpMethod.POST, "/api/authenticate").permitAll()
//                    .antMatchers(HttpMethod.GET, "/api/users").permitAll()
//                    .antMatchers(HttpMethod.GET, "/api/users/*").permitAll()
                .and()
/** -- CSRF -- **/
//                .csrf().disable()
                .csrf().ignoringAntMatchers("/api/authenticate").and()
                .csrf().csrfTokenRepository(csrfTokenRepository())
                .and()
                .addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
/** ---- **/
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