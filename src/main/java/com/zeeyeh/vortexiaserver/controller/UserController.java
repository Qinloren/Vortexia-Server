package com.zeeyeh.vortexiaserver.controller;

import com.zeeyeh.vortexiaserver.data.dto.*;
import com.zeeyeh.vortexiaserver.data.entity.Result;
import com.zeeyeh.vortexiaserver.data.vo.UserSearchVo;
import com.zeeyeh.vortexiaserver.data.vo.UserVo;
import com.zeeyeh.vortexiaserver.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "用户管理接口", description = "用户相关操作接口")
public class UserController {

    @Resource
    UserService userService;

    @PostMapping("/create")
    @Operation(summary = "创建用户", method = "POST")
    @ResponseBody
    public UserVo create(@RequestBody UserCreateDto createDto) {
        return userService.create(createDto);
    }

    @PostMapping("/login")
    @ResponseBody
    @Operation(summary = "用户登录", method = "POST")
    public Result<UserVo> login(@RequestBody UserLoginDto loginDto) {
        return userService.login(loginDto);
    }


    @GetMapping("/search")
    @ResponseBody
    @Operation(summary = "用户搜索", method = "GET")
    public UserSearchVo search(@RequestBody UserSearchDto searchDto) {
        return userService.search(searchDto);
    }

    @PostMapping("/update")
    @ResponseBody
    @Operation(summary = "更新用户信息", method = "POST")
    public UserVo update(@RequestBody UserUpdateDto updateDto) {
        return userService.update(updateDto);
    }

    @PostMapping("/delete")
    @ResponseBody
    @Operation(summary = "删除用户", method = "POST")
    public void delete(@RequestBody UserDeleteDto deleteDto) {
        userService.delete(deleteDto);
    }
}
