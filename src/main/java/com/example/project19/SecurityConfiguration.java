package com.example.project19;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity

public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
  // @Override
  // protected void configure(AuthenticationManagerBuilder auth) throws Exception {
   //   auth.inMemoryAuthentication().withUser("user").password(encoder().encode("password")).authorities("USER");
  // };
       @Override protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.inMemoryAuthentication().
      withUser("dave").password(encoder().encode("password")).authorities("ADMIN")
               .and().
      withUser("user").password(encoder().encode("password")).authorities("USER");
    

  }
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .antMatchers("/")
                .access("hasAnyAuthority('USER','ADMIN')")
                .antMatchers("/admin").access("hasAuthority('Admin')")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .httpBasic();
    }
    @Bean
    PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}


