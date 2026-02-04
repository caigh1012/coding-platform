package cn.caigh.coding_platform.controller;

import cn.caigh.coding_platform.pojo.entity.User;
import cn.caigh.coding_platform.pojo.vo.common.ResultVo;
import cn.caigh.coding_platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
