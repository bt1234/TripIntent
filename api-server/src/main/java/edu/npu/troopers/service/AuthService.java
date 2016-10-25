package edu.npu.troopers.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import edu.npu.troopers.bean.User;

@Service
public class AuthService extends AbstractAuthService{

  private static final String AUTH_TOKEN = "e8584a59dca28e4219f8df9g79d1b7d48981";
  private static final Logger logger = Logger.getLogger(AuthService.class);
  
  @Autowired
  private DBService dbService;
  
  public void authenticateClient(String accessToken) throws AuthenticationException{
    if(!AUTH_TOKEN.equals(accessToken))throw new BadCredentialsException("Unauthorized client");
  }

  @Override
  public Authentication authenticate(Authentication auth) throws AuthenticationException {
    String username = auth.getName();
    String password = auth.getCredentials().toString();
    
    User user = verifyCredentials(username, password);
    if(user != null){
      edu.npu.troopers.bean.Authentication authentication = new edu.npu.troopers.bean.Authentication(user);
      authentication.setAuthenticated(true);
      
      return authentication;
    }
    
    throw new BadCredentialsException("Invalid username, password combination.");
  }

  @Override
  public String addUser(String email, String password, String firstName, String lastName,
      boolean enabled) throws Exception {
    
    if (!StringUtils.isEmpty(email) && !StringUtils.isEmpty(password)
        && !StringUtils.isEmpty(firstName) && !StringUtils.isEmpty(lastName)) {
      
      String activationCode = UUID.randomUUID().toString();
      
      String sql = "insert into user (email, password, first_name, last_name, activation_code, active) values(:email, password(:password), :first_name, :last_name, :activation_code, :active)";
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("email", email.toLowerCase());
      params.put("password", password);
      params.put("first_name", firstName);
      params.put("last_name", lastName);
      params.put("active", false);
      params.put("activation_code", activationCode);
      
      try {
        dbService.update(sql, params);
        return activationCode;
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        throw new Exception("Email has already been registered", e);
      }
    }
    
    throw new Exception("Missing fields in the request");
  }

  @Override
  public User activateUser(String email, String activationCode) throws Exception {
    if(!StringUtils.isEmpty(email) && !StringUtils.isEmpty(activationCode)){
      email = email.toLowerCase();
      String sql = "select * from user where email = :email and activation_code = :activation_code";
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("email", email);
      params.put("activation_code", activationCode);
      
      try {
        Map<String, Object> userObj = dbService.queryForObject(sql, params);
        
        if(userObj != null){
          User user = new User(email, null, (String)userObj.get("first_name"), (String)userObj.get("last_name"), ((Integer)userObj.get("active")) == 1 ? true : false);
          
          //Activate user
          String activateSql = "update user set activation_code = ?, active = ? where email = ?";
          dbService.update(activateSql, null, true, email);
          user.setEnabled(true);
          
          return user;
        }
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
      }
    }
    
    throw new Exception("Invalid username and/or activation code");
  }

  @Override
  public void removeUser(String email) throws Exception {
    
  }

  @Override
  public void changePassword(String email, String oldPassword, String newPassword)
      throws Exception {
  }

  @Override
  public User verifyCredentials(String email, String password) throws AuthenticationException {
    if(!StringUtils.isEmpty(email) && !StringUtils.isEmpty(password)){
      String sql = "select * from user where email = :email and password = password(:password) and active = :active";
      Map<String, Object> params = new HashMap<String, Object>();
      params.put("email", email.toLowerCase());
      params.put("password", password);
      params.put("active", true);
      
      List<Map<String, Object>> results = dbService.queryForList(sql, params);
      
      if(results != null && !results.isEmpty()){
        Map<String, Object> userObj = results.get(0);
        User user = new User(email, null, (String)userObj.get("first_name"), (String)userObj.get("last_name"), ((Integer)userObj.get("active")) == 1 ? true : false);
        return user;
      }
    }
    return null;
  }

  @Override
  public List<User> listAllUsers() {
    return null;
  }

  @Override
  public List<User> searchUsers(String keyword) throws Exception {
    return null;
  }

}