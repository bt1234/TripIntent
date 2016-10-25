package edu.npu.troopers.bean;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class Authentication implements org.springframework.security.core.Authentication{
  
  private static final long serialVersionUID = 1L;

  private User user = null;
  private String credentials = null;
  boolean isAuthenticated = false;
  
  public Authentication(User user) {
    this(user, user != null);
  }
  
  public Authentication(User user, boolean isAuthenticated) {
    this.user = user;
    this.isAuthenticated = true;
    if(user != null){
      this.isAuthenticated = true;
    }
  }

  @Override
  public String getName() {
    return user != null ? user.getUsername() : null;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return user != null ? user.getAuthorities() : null;
  }

  @Override
  public Object getCredentials() {
    return this.credentials;
  }

  @Override
  public Object getDetails() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return user;
  }
  
  public User getUser() {
    return user;
  }
  
  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public boolean isAuthenticated() {
    return isAuthenticated;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    this.isAuthenticated = isAuthenticated;
  }

}
