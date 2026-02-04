package cn.caigh.coding_platform.service.impl;

import cn.caigh.coding_platform.dao.UserDao;
import cn.caigh.coding_platform.pojo.entity.User;
import cn.hutool.core.util.ObjectUtil;
import jakarta.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Resource
  UserDao userDao;

  /**
   * 登录时调用
   * 注意：
   * UsernameNotFoundException 在全局异常中的 BadCredentialsException 触发
   */
  @Override
  public UserDetails loadUserByUsername(String username) {
    User user = userDao.getUserInfo(username);
    if (ObjectUtil.isNull(user)) {
      // 该 “用户未注册，请先注册” 不会返回到前端
      throw new UsernameNotFoundException("用户未注册，请先注册");
    }
    return user;
  }
}
