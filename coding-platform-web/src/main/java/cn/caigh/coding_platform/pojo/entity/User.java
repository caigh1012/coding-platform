package cn.caigh.coding_platform.pojo.entity;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties({"password", "enabled", "accountNonLocked", "credentialsNonExpired", "accountNonExpired"})
public class User implements UserDetails {
  private static final Logger log = LoggerFactory.getLogger(User.class);
  private Integer id;
  private String uni_phone_number;
  private String password;
  private String role_id;
  private Date created_at;
  private String is_active;

  private List<SimpleGrantedAuthority> authorities;

  public User() {
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", uni_phone_number='" + uni_phone_number + '\'' +
        ", password='" + password + '\'' +
        ", role_id='" + role_id + '\'' +
        ", created_at=" + created_at +
        ", is_active='" + is_active + '\'' +
        ", authorities=" + authorities +
        '}';
  }

  @Override
  @JsonIgnore
  public List<SimpleGrantedAuthority> getAuthorities() {
    if (ObjectUtil.isNull(role_id)) {
      return List.of();
    }
    List<String> roleList = Arrays.asList(role_id.split(","));
    authorities = roleList.stream()
        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
        .collect(Collectors.toList());
    return authorities;
  }

  @JsonGetter("authorities")
  public List<String> getAuthoritiesString() {
    return Arrays.asList(role_id.split(","));
  }

  @JsonSetter("authorities")
  public void setAuthoritiesString(List<String> list) {
    this.role_id = StrUtil.join(",", list);
  }

  @Override
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

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getRole_id() {
    return role_id;
  }

  public void setRole_id(String role_id) {
    this.role_id = role_id;
  }

  public Date getCreated_at() {
    return new DateTime(created_at);
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
