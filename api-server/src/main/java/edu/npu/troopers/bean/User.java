package edu.npu.troopers.bean;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails{
  private static final long serialVersionUID = 1L;
  
  private String email;
  private String password;
  private String firstName;
  private String lastName;
  
  private boolean enabled = true;
  private Long lastLoggedIn;
  
  public User(String email, String password, String firstName, String lastName, boolean enabled) {
    this.email = email;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.enabled = enabled;
  }
  
  @SuppressWarnings("serial")
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singleton(new GrantedAuthority() {
      @Override
      public String getAuthority() {
        return "USER";
      }
    });
  }
  
  @Override
  public String getPassword() {
    return this.password;
  }
  @Override
  public String getUsername() {
    return email;
  }
  @Override
  public boolean isAccountNonExpired() {
    return false;
  }
  @Override
  public boolean isAccountNonLocked() {
    return false;
  }
  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }
  @Override
  public boolean isEnabled() {
    return this.enabled;
  }
  
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
  
  public String getEmail() {
    return email;
  }
  
  public String getFirstName() {
    return firstName;
  }
  
  public String getLastName() {
    return lastName;
  }
  
  public Long getLastLoggedIn() {
    return lastLoggedIn;
  }
  
  public void setLastLoggedIn(Long lastLoggedIn) {
    this.lastLoggedIn = lastLoggedIn;
  }
}
