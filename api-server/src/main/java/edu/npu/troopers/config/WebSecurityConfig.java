  package edu.npu.troopers.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import edu.npu.troopers.service.AuthService;

@EnableWebSecurity
@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  
  @Autowired
  private AuthService authService;
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    
    http.antMatcher("/**")
      .addFilterBefore(new ApiCorsFilter(), BasicAuthenticationFilter.class)
      //.addFilterAfter(new ApiAuthenticationFilter(authService), ApiCorsFilter.class)
      .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS).and()
      .csrf()
        .disable()
      .headers()
        .xssProtection().block(true).and()
        .frameOptions().sameOrigin()
        .httpStrictTransportSecurity()
          .includeSubDomains(false)
          .maxAgeInSeconds(31536000)
          .and()
        .and()
      .authorizeRequests()
        .antMatchers("/api/auth/**").permitAll()
        .antMatchers("/api/**").authenticated()
        .anyRequest().denyAll();
      ;
  }
  
  @Override
  protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
    authManagerBuilder.authenticationProvider(authService);
  }

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
}

