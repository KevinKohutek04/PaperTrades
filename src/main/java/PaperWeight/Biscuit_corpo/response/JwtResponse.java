package PaperWeight.Biscuit_corpo.response;

import PaperWeight.Biscuit_corpo.entity.Roles;

import java.util.Set;

public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private String username;
  private Set<Roles> roles;

  public JwtResponse(String accessToken, String username, Set<Roles> roles) {
    this.token = accessToken;
    this.username = username;
    this.roles = roles;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Set<Roles> getRoles() {
    return roles;
  }

  public void setRoles(Set<Roles> roles) {
    this.roles = roles;
  }
}