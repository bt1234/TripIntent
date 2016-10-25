package edu.npu.troopers.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.AuthenticationException;

import edu.npu.troopers.bean.User;

public abstract class AbstractAuthService implements AuthenticationProvider {

  public abstract String addUser(String email, String password, String firstName, String lastName, boolean enabled) throws Exception;
  public abstract User activateUser(String email, String activationCode) throws Exception;
  public abstract void removeUser(String email) throws Exception;
  public abstract void changePassword(String email, String oldPassword, String newPassword) throws Exception;
  public abstract User verifyCredentials(String email, String password)throws AuthenticationException;
  
  public abstract List<User> listAllUsers();
  public abstract List<User> searchUsers(String keyword) throws Exception;
  
  @Override
  public boolean supports(Class<?> arg0) {
    return true;
  }
  
}
