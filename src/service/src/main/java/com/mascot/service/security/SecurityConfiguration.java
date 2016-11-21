package com.mascot.service.security;

import com.mascot.common.MailSender;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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
//                final String password = new String(encode );
                // todo find in cache and if not found find in database;
                username = decodeUTF8(username);
                final UserService userService = MascotAppContext.getBean(UserService.class);
                final com.mascot.server.model.User appUser = userService.loadUserByLogin(username);
                if (appUser == null) {
                    final String msg = "Not found user with login '" + username + "'";
                    logger.info(msg);
                    throw new UsernameNotFoundException(msg);
                }
                final List<GrantedAuthority> authorities = appUser.getRoles().stream().
                        map(role -> new SimpleGrantedAuthority(role.getName())).
                        collect(Collectors.toList());
                final byte[] encode = Base64.decode(appUser.getPassword().getBytes());
                final Locale locale = appUser.getLocale() != null ? appUser.getLocale() : Locale.getDefault();
                return new MascotSession(username, new String(encode), authorities, appUser.getId(), locale);
            }
        });

/*
        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
*/
    }

    private String decodeUTF8(String username) {
        try {
            return URLDecoder.decode(username, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            MailSender.sendErrorAsync("Unable decode username: '" + username + "'", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/authenticate").permitAll()
//                    .antMatchers(HttpMethod.POST, "/api/users").permitAll()
//                    .antMatchers(HttpMethod.GET, "/api/users/*").permitAll()
                .and()
// -- CSRF configuration
//                .csrf().disable()
                .csrf().ignoringAntMatchers("/api/authenticate", "/logout").and()
                .csrf().csrfTokenRepository(csrfTokenRepository())
                .and()
                .addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
// -- end CSRF configuration
                .httpBasic()
                .and()
// -- Logout configuration
                .logout().
                logoutUrl("/logout").
                logoutSuccessUrl("/").
                addLogoutHandler(createLogoutHandler());
// -- End logout configuration

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

    private LogoutHandler createLogoutHandler() {
        return new LogoutHandler() {

            @Override
            public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
/*
                try {
                    Thread.currentThread().sleep(1000);
                } catch (InterruptedException e) {

                }
*/
                if (authentication != null) {
                    final User principal = (User) authentication.getPrincipal();
                    logger.info("Success Logout: " + principal.getUsername());
                } else {
                    logger.info("Success logout but authentication is null");
                }


            }
        };
    }
}
