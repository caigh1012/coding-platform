package cn.caigh.coding_platform.controller;

import cn.caigh.coding_platform.pojo.entity.Menu;

import cn.caigh.coding_platform.pojo.vo.common.ResultVo;
import cn.caigh.coding_platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserCtrl {
  @Autowired
  private UserService userService;

  /**
   * 查询用户的菜单列表
   */
  @GetMapping(value = "/user/menu.json")
  public ResultVo<List<Menu>> menuList(@AuthenticationPrincipal UserDetails userDetails) {
    String username = userDetails.getUsername();
    List<Menu> menuList = userService.menuListByMobile(username);

    return ResultVo.success(menuList);
  }

  /**
   * 查询用户的角色列表
   */
  @GetMapping(value = "/user/role.json")
  public ResultVo<List<String>> roleList(@AuthenticationPrincipal UserDetails userDetails) {
    String username = userDetails.getUsername();
    List<String> roleList = userService.roleListByMobile(username);
    return ResultVo.success(roleList);
  }
}
