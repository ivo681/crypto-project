package com.example.backend.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    @Autowired
    public ApplicationSecurityConfig(UserDetailsService userDetailsService,
                                     JwtAuthenticationEntryPoint unauthorizedHandler, PasswordEncoder passwordEncoder, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
        this.passwordEncoder = passwordEncoder;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

//    @Override
//    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
////        authenticationManagerBuilder
////                .userDetailsService(this.userDetailsService)
////                .passwordEncoder(this.passwordEncoder);
//        authenticationManagerBuilder
//                .inMemoryAuthentication()
//                .withUser("user@abv.bg")
//                .password("password")
//                .roles("USER");
//    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/users/login").
//                antMatchers(HttpMethod.OPTIONS, "/**");
////        super.configure(web);
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(this.passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/users/login", "/users/register").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic();

//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/users/login", "/users/register").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/")
//                .loginProcessingUrl("/auth")
//                .usernameParameter("email")
//                .passwordParameter("password")
//                .successHandler(successHandler())
//                .failureHandler(failureHandler())
//                .permitAll()
//                .and()
//                .logout().permitAll();

        http
                .cors()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(this.unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/users/**")
                .anonymous()
                .antMatchers(HttpMethod.GET, "/api/courses/**", "/api/gatherings/**", "/api/resorts/**")
                .permitAll()
                .antMatchers(HttpMethod.POST, "/api/courses").hasAuthority("ROLE_ADMIN")
                .antMatchers("/api/logs/**", "/api/users", "/api/roles/**").hasAuthority("ROLE_ADMIN")
                .anyRequest()
                .authenticated();

        http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }

//    private AuthenticationSuccessHandler successHandler() {
//        return new AuthenticationSuccessHandler() {
//            @Override
//            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
//                                                HttpServletResponse httpServletResponse, Authentication authentication)
//                    throws IOException, ServletException {
//                httpServletResponse.getWriter().append("OK");
//                httpServletResponse.setStatus(200);
//            }
//        };
//    }

//    private AuthenticationFailureHandler failureHandler() {
//        return new AuthenticationFailureHandler() {
//            @Override
//            public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
//                                                HttpServletResponse httpServletResponse, AuthenticationException e)
//                    throws IOException, ServletException {
//                httpServletResponse.getWriter().append("Authentication failure");
//                httpServletResponse.setStatus(401);
//            }
//        };
//    }

}
