/*
    NOTICE:
    this class is an adaptation of
    https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/spring/security/authorization/src/main/java/org/tsdes/intro/spring/security/authorization/WebSecurityConfig.java

    And the concept of authorization is adapted from
    https://www.baeldung.com/spring-security-expressions section 4.1
    https://www.baeldung.com/spring-security-custom-access-denied-page
*/


package no.kristiania.exam.pg5100.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return super.userDetailsServiceBean();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) {
        try {
            http.csrf().disable();
            http.authorizeRequests()
                    .antMatchers("/", "/index.jsf", "/signup.jsf", "/assets/**").permitAll()
                    .antMatchers("/javax.faces.resource/**").permitAll()
                    .antMatchers("/ui/**").authenticated()
                    .antMatchers("/admin/**").access("hasRole('ADMIN')")
                    .anyRequest().authenticated()
                    .and()
                    .exceptionHandling()
                    .accessDeniedPage("/accessDenied.jsf")
                    .and()
                    .formLogin()
                    .loginPage("/login.jsf")
                    .permitAll()
                    .failureUrl("/login.jsf?error=true")
                    .defaultSuccessUrl("/index.jsf")
                    .and()
                    .logout()
                    .logoutSuccessUrl("/index.jsf");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {

        try {
            auth.jdbcAuthentication()
                    .dataSource(dataSource)
                    .usersByUsernameQuery(
                            "SELECT username, password, enabled " +
                                    "FROM users " +
                                    "WHERE username = ?"
                    )
                    .authoritiesByUsernameQuery(
                            "SELECT x.username, y.roles " +
                                    "FROM users x, user_roles y " +
                                    "WHERE x.username = ? and y.user_username = x.username "
                    )
                    /*
                        Note: in BCrypt, the "password" field also contains the salt
                     */
                    .passwordEncoder(passwordEncoder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
