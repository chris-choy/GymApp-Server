package com.chris.gym.config;

import com.chris.gym.User.GymUserDetailService;
import com.chris.gym.jwt.JWTConfigurer;
import com.chris.gym.jwt.JwtAccessDeniedHandler;
import com.chris.gym.jwt.JwtAuthenticationEntryPoint;
import com.chris.gym.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

//    @Bean
//    @Override
//    public UserDetailsService userDetailsService(){
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("user")
//                .password("user")
//                .roles("USER")
//                .build()
//        );
//        return manager;
//    }

    private final TokenProvider tokenProvider;
    private final GymUserDetailService gymUserDetailService;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint authenticationErrorHandler;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public WebSecurity(TokenProvider tokenProvider,
                       GymUserDetailService gymUserDetailService, CorsFilter corsFilter, JwtAuthenticationEntryPoint authenticationErrorHandler, JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.tokenProvider = tokenProvider;
        this.gymUserDetailService = gymUserDetailService;
        this.corsFilter = corsFilter;
        this.authenticationErrorHandler = authenticationErrorHandler;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }


//    public WebSecurity(TokenProvider tokenProvider) {
//        this.tokenProvider = tokenProvider;
//    }


    @Override
    public void configure(org.springframework.security.config.annotation.web.builders.WebSecurity web) throws Exception {
        super.configure(web);
        web.ignoring().antMatchers("/v2/api-docs",
                "/v3/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                // 自定义基于JWT.
                // 在UsernamePasswordAuthenticationFilter之前加入corsFilter。
                 .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                // ？
                .exceptionHandling()
                // 入口？
                .authenticationEntryPoint(authenticationErrorHandler)
                //
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()

                // create no session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .authorizeRequests()
//                .antMatchers("/hi").permitAll()
//                .antMatchers("/pt").permitAll()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/swagger-ui/*").permitAll()
//                .antMatchers("/hello").hasRole("USER")
//                .antMatchers("/admin").hasRole("ADMIN")
//                .antMatchers("/plans").hasRole("USER")
//                .antMatchers("/plans").hasAuthority("USER")
//                .antMatchers("/plan/create").hasAuthority("USER")
                .antMatchers("/plan/*").hasAuthority("USER")
                .antMatchers("/sport/*").hasAuthority("USER")
                .antMatchers("/user/*").hasAuthority("USER")
                .antMatchers("/hello/*").permitAll()
//                .antMatchers("/hello").hasRole("USER")
//                .antMatchers("/hello").hasAuthority("USER")
                .anyRequest().authenticated()
                .and()
                .apply(securityConfigurerAdapter())
                ;
    }

    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        /*
        auth.inMemoryAuthentication()
                .withUser("user").password(passwordEncoder().encode("123456")).roles("USER")
                .and()
                .withUser("11").password(passwordEncoder().encode("11")).roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN")
                .and()
                .passwordEncoder(passwordEncoder());//配置BCrypt加密


         */


        auth.userDetailsService(gymUserDetailService);


    }





    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }

}
