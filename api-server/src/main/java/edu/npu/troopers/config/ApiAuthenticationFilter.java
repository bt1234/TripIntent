package edu.npu.troopers.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.filter.GenericFilterBean;

import edu.npu.troopers.service.AuthService;

public class ApiAuthenticationFilter extends GenericFilterBean{
  
  private static final Logger logger = Logger.getLogger(ApiAuthenticationFilter.class);
  
  private final String HEADER_X_AUTH_TOKEN = "X-Auth-Token";
  private AuthService authService;
  
  public ApiAuthenticationFilter(AuthService authService) {
    this.authService = authService;
  }
  
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;
    
    String accessToken = req.getHeader(HEADER_X_AUTH_TOKEN);
    
    if(StringUtils.isEmpty(accessToken)){
      logger.error("API Access Token is missing for :"+req.getRequestURI());
      res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
    try {
      authService.authenticateClient(accessToken);
      chain.doFilter(request, response);
    } catch (AuthenticationException e) {
      logger.error("Unauthorized API Access Attempt with Access Token:"+accessToken);
      res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
    
  }

}
