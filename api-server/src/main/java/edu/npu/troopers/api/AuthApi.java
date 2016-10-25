package edu.npu.troopers.api;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.npu.troopers.ApiPathConstants;
import edu.npu.troopers.bean.User;
import edu.npu.troopers.service.AuthService;

@RestController
@RequestMapping(value = ApiPathConstants.AUTH_BASE)
public class AuthApi {

  private final AuthenticationManager authenticationManager;
  private static final Logger logger = Logger.getLogger(AuthApi.class);
  
  @Autowired
  AuthService authService;
  
  @Autowired
  public AuthApi(AuthenticationManager am) {
    this.authenticationManager = am;
  }

  @RequestMapping(value = ApiPathConstants.AUTH_LOGIN, method = {RequestMethod.POST, RequestMethod.GET})
  public ResponseEntity<?> authenticate(@RequestParam String email, @RequestParam String password) {

    UsernamePasswordAuthenticationToken authToken =
        new UsernamePasswordAuthenticationToken(email, password);
    
    Authentication authentication = null;
    try {
      authentication = this.authenticationManager.authenticate(authToken);
    } catch (AuthenticationException e) {
      logger.error(e.getMessage());
      Map<String, Object> res = new HashMap<>();
      res.put("msg", e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
    }
    
    SecurityContextHolder.getContext().setAuthentication(authentication);

    if (authentication.isAuthenticated()) {
      User user = (User)authentication.getPrincipal();
      HttpHeaders headers = new HttpHeaders();
      return new ResponseEntity<>(user, headers, HttpStatus.OK);
    }else{
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }
  
  @RequestMapping(value = ApiPathConstants.AUTH_SIGNUP, method = {RequestMethod.POST})
  public ResponseEntity<?> signup(@RequestParam String email, @RequestParam String password,
      @RequestParam String firstName, @RequestParam String lastName) {

    try {
      String activationCode = authService.addUser(email, password, firstName, lastName, false);
      return ResponseEntity.status(HttpStatus.OK).body(activationCode);
    } catch (Exception e1) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e1.getMessage());
    }
    
  }
  
  @RequestMapping(value = ApiPathConstants.AUTH_ACTIVATE, method = {RequestMethod.POST, RequestMethod.GET})
  public ResponseEntity<?> activate(@RequestParam String email, @RequestParam String activationCode) {

    try {
      User user = authService.activateUser(email, activationCode);
      return ResponseEntity.status(HttpStatus.OK).body(user);
    } catch (Exception e1) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e1.getMessage());
    }
    
  }

  @RequestMapping(value = ApiPathConstants.AUTH_LOGOUT, method = {RequestMethod.POST, RequestMethod.GET})
  public void logout() {
    SecurityContextHolder.getContext().setAuthentication(null);
  }

  @RequestMapping(value = ApiPathConstants.AUTH_PING, method = {RequestMethod.GET, RequestMethod.OPTIONS})
  public ResponseEntity<?> ping() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
      if (authentication.isAuthenticated()) {
        User user = (User)authentication.getPrincipal();
        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<>(user, headers, HttpStatus.OK);
      }else{
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }
  
}
