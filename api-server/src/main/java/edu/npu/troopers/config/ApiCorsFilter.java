package edu.npu.troopers.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

public class ApiCorsFilter extends GenericFilterBean {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse res = (HttpServletResponse) response;

    res.setHeader("Access-Control-Allow-Origin", "*");
    if (req.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(req.getMethod())); {
      res.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
      res.setHeader("Access-Control-Allow-Headers", "X-Auth-Token, X-Requested-With");
      res.setHeader("Access-Control-Max-Age", "3600");
    }

    if (req.getMethod() != "OPTIONS") {
      chain.doFilter(req, res);
    }
  }

}
