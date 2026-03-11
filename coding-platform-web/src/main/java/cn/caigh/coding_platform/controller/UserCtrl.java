package cn.caigh.coding_platform.controller;

import cn.caigh.coding_platform.pojo.dto.user.DeleteUserDto;
import cn.caigh.coding_platform.pojo.entity.Menu;
import cn.caigh.coding_platform.pojo.entity.Role;
import cn.caigh.coding_platform.pojo.entity.User;
import cn.caigh.coding_platform.pojo.vo.common.ResultVo;
import cn.caigh.coding_platform.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserCtrl {
  @Autowired
  private UserService userService;

  @GetMapping(value = "/user/list.json")
  public ResultVo<List<User>> userList() {
    List<User> userList = userService.userList();
    return ResultVo.success(userList);
  }

  @GetMapping(value = "/user/menu.json")
  public ResultVo<List<Menu>> menuList() {
    List<Menu> menuList = userService.menuList();
    return ResultVo.success(menuList);
  }

  @PostMapping(value = "/user/delete.json")
  public ResultVo<String> deleteUser(@RequestBody @Valid DeleteUserDto deleteUserDto) {
    int num = userService.deleteUser(deleteUserDto);
    if (num >= 1) {
      return ResultVo.success("禁用用户成功");
    } else {
      return ResultVo.failed("禁用用户失败");
    }
  }

  @PostMapping(value = "/user/add.json")
  public ResultVo<String> addUser() {
    return ResultVo.success("禁用用户成功");
  }

  @GetMapping(value = "/role.json")
  public ResultVo<List<Role>> roleList() {
    List<Role> roleList = userService.roleList();
    return ResultVo.success(roleList);
  }
}
