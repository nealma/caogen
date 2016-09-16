package com.caogen.config;

import com.caogen.security.MyAuthenticationProvider;
import com.caogen.security.MyPersistentTokenRepository;
import com.caogen.security.MyUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by neal on 9/12/16.
 */
@Configuration
@ComponentScan("com.caogen")
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PropertySource("classpath:application.properties")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private MyAuthenticationProvider provider;//自定义验证

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private MyPersistentTokenRepository persistentTokenRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>{}<<<<<<<<<<<<<<<<<<<<<<<<", "configure");
        http.
                headers().frameOptions().sameOrigin().disable()//disable X-Frame-Options
                .authorizeRequests()
                .antMatchers("/").permitAll()//放行／请求
                .antMatchers("/users/**").hasAuthority("neal")
                .anyRequest().fullyAuthenticated()//其他url需要鉴权
                .and()
                    .formLogin()
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .loginProcessingUrl("/login")
                    .loginPage("/login")
                    .failureUrl("/login?error")
                    .permitAll()
                .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
                .and()
                    .rememberMe().userDetailsService(userDetailsService)
                                    .tokenRepository(persistentTokenRepository)
                                    .rememberMeParameter("remember-me").key("userLoginKey")
                                    .tokenValiditySeconds(86400)
                .and()
                    .csrf().disable(); //disable csrf
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>{}<<<<<<<<<<<<<<<<<<<<<<<<", "configureGlobal");
        //将验证过程交给自定义验证工具
        auth.authenticationProvider(provider);
    }

}
