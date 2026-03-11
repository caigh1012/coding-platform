package cn.caigh.coding_platform.service.impl;

import cn.caigh.coding_platform.constants.DefaultRole;
import cn.caigh.coding_platform.constants.RedisKey;
import cn.caigh.coding_platform.dao.UserDao;
import cn.caigh.coding_platform.pojo.dto.login.PwdLoginDto;
import cn.caigh.coding_platform.pojo.dto.login.UserRegisterDto;
import cn.caigh.coding_platform.pojo.entity.User;
import cn.caigh.coding_platform.pojo.vo.login.LoginVo;
import cn.caigh.coding_platform.service.LoginService;
import cn.caigh.coding_platform.pojo.vo.common.ResultVo;
import cn.caigh.coding_platform.utils.JwtUtil;
import cn.caigh.coding_platform.utils.RedisUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {
  @Autowired
  UserDao userDao;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private RedisUtil redisUtil;

  /**
   * 用户注册
   */
  public ResultVo<String> register(UserRegisterDto userRegisterDto) {
    User user = userDao.getUserInfo(userRegisterDto.getUsername());
    // user 如果为 null，说明表里不存在可以进行添加
    if (user == null) {
      if (!Objects.equals(userRegisterDto.getPassword(), userRegisterDto.getPasswordConfirm())) {
        return ResultVo.failed("两次输入的密码不一致");
      }
      User userInfo = new User();
      userInfo.setUsername(userRegisterDto.getUsername());
      userInfo.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
      // 注册的用户默认设置管理员角色
      userInfo.setRole_id(DefaultRole.Admin.getRoleId());
      DateTime now = DateTime.now();
      userInfo.setCreated_at(now);
      userDao.registerUser(userInfo);
      return ResultVo.success(null, "用户注册成功");
    }
    return ResultVo.failed("该用户已经注册过了");
  }

  /**
   * 密码登录
   */
  public LoginVo pwdLogin(PwdLoginDto pwdLoginDto) {
    // 1.使用框架自带方法(已重写)进行验证登录
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(pwdLoginDto.getUsername(), pwdLoginDto.getPassword());

    // 2.开始验证：自动调用 MyUserDetailsService.loadUserByUsername() 方法
    Authentication authenticate = authenticationManager.authenticate(authenticationToken);

    // 3.登录成功
    User user = (User) authenticate.getPrincipal();
    String username = user.getUsername();

    // 4.生成redis存储的key, 这里使用手机号作为key
    // appId:token:username
    String tokenKey = RedisKey.getTokenKey(username);
    // 生成此时 uuid, 不同时期的登录uuid不同
    String uuid = IdUtil.simpleUUID();

    redisUtil.set(tokenKey, uuid); // 不设置过期

    String userKey = RedisKey.getUserKey(uuid);

    redisUtil.set(userKey, user, 1800); // 使用 uuid 作为key，存储用户信息

    // 5.生成token
    String token = JwtUtil.generateToken(username, uuid);
    LoginVo result = new LoginVo();
    result.setToken(token);
    return result;
  }

  /**
   * 退出登录
   */
  public ResultVo<String> loginout(String username) {
    String tokenKey = RedisKey.getTokenKey(username);
    String uuid = (String) redisUtil.get(tokenKey);
    String userKey = RedisKey.getUserKey(uuid);

    redisUtil.delete(tokenKey);
    redisUtil.delete(userKey);

    // 清除SecurityContext（防止在当前请求后续操作中仍使用旧认证）
    SecurityContextHolder.clearContext();
    return ResultVo.success(null, "退出登录成功");
  }
}
