package cn.caigh.coding_platform.pojo.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;


@JsonIgnoreProperties({"password", "enabled", "accountNonLocked", "credentialsNonExpired", "accountNonExpired", "authorities"})
public class User implements UserDetails {
  private static final Logger log = LoggerFactory.getLogger(User.class);
  private Integer id;
  private String uni_phone_number;
  private String password;
  private List<String> roleList;
  private Date created_at;
  private String is_active;

  public User() {
  }

  public User(Integer id, String uni_phone_number, String password, List<String> roleList, Date created_at, String is_active) {
    this.id = id;
    this.uni_phone_number = uni_phone_number;
    this.password = password;
    this.roleList = roleList;
    this.created_at = created_at;
    this.is_active = is_active;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", uni_phone_number='" + uni_phone_number + '\'' +
        ", password='" + password + '\'' +
        ", roleList=" + roleList +
        ", created_at=" + created_at +
        ", is_active='" + is_active + '\'' +
        '}';
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return uni_phone_number;
  }

  public void setUsername(String uni_phone_number) {
    this.uni_phone_number = uni_phone_number;
  }

  @Override
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<String> getRoleList() {
    return roleList;
  }

  public void setRoleList(List<String> roleList) {
    this.roleList = roleList;
  }

  public Date getCreated_at() {
    return created_at;
  }

  public void setCreated_at(Date created_at) {
    this.created_at = created_at;
  }

  public String getIs_active() {
    return is_active;
  }

  public void setIs_active(String is_active) {
    this.is_active = is_active;
  }
}
