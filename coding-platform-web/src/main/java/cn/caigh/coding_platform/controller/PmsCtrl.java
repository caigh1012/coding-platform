package cn.caigh.coding_platform.controller;

import cn.caigh.coding_platform.pojo.dto.pms.AddUserDto;
import cn.caigh.coding_platform.pojo.dto.pms.DeleteUserDto;
import cn.caigh.coding_platform.pojo.entity.Menu;
import cn.caigh.coding_platform.pojo.entity.Role;
import cn.caigh.coding_platform.pojo.entity.User;
import cn.caigh.coding_platform.pojo.vo.common.ResultVo;
import cn.caigh.coding_platform.pojo.vo.login.CaptchaVerifyVo;
import cn.caigh.coding_platform.service.CaptchaService;
import cn.caigh.coding_platform.service.PmsService;
import cn.caigh.coding_platform.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PmsCtrl {
  @Autowired
  private PmsService pmsService;

  @Autowired
  private UserService userService;
  @Autowired
  private CaptchaService captchaService;

  /**
   * 用户列表（全部用户）
   */
  @GetMapping(value = "/pms/userlist.json")
  public ResultVo<List<User>> userList() {
    List<User> userList = pmsService.userList();
    return ResultVo.success(userList);
  }

  /**
   * 菜单列表（全菜单）
   */
  @GetMapping(value = "/pms/menulist.json")
  public ResultVo<List<Menu>> menuList() {
    List<Menu> menuList = pmsService.menuList();
    return ResultVo.success(menuList);
  }

  /**
   * 角色列表（全部角色）
   */
  @GetMapping(value = "/pms/rolelist.json")
  public ResultVo<List<Role>> roleList() {
    List<Role> roleList = pmsService.roleList();
    return ResultVo.success(roleList);
  }

  /**
   * 删除用户
   */
  @PostMapping(value = "/pms/deleteuser.json")
  public ResultVo<String> deleteUser(@RequestBody @Valid DeleteUserDto deleteUserDto) {
    int num = pmsService.deleteUser(deleteUserDto);
    if (num >= 1) {
      return ResultVo.success("禁用用户成功");
    } else {
      return ResultVo.failed("禁用用户失败");
    }
  }

  /**
   * 添加用户
   */
  @PostMapping(value = "/pms/adduser.json")
  public ResultVo<String> addUser(@RequestBody @Valid AddUserDto addUserDto) {
    CaptchaVerifyVo captchaVerifyVo = captchaService.verifyCaptcha(addUserDto.getCaptchaId(), addUserDto.getCaptchaCode());
    if (!captchaVerifyVo.isVerifyPass()) {
      return ResultVo.failed(captchaVerifyVo.getVerifyMessage());
    }
    return pmsService.addUser(addUserDto);
  }
}
