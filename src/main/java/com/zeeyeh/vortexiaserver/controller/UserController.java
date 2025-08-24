package com.zeeyeh.vortexiaserver.controller;

import com.zeeyeh.vortexiaserver.data.dto.*;
import com.zeeyeh.vortexiaserver.data.entity.Result;
import com.zeeyeh.vortexiaserver.data.vo.UserSearchVo;
import com.zeeyeh.vortexiaserver.data.vo.UserVo;
import com.zeeyeh.vortexiaserver.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @PostMapping("/create")
    @ResponseBody
    public UserVo create(@RequestBody UserCreateDto createDto) {
        return userService.create(createDto);
    }

    @PostMapping("/login")
    @ResponseBody
    public Result<UserVo> login(@RequestBody UserLoginDto loginDto) {
        return userService.login(loginDto);
    }


    @GetMapping("/search")
    @ResponseBody
    public UserSearchVo search(@RequestBody UserSearchDto searchDto) {
        return userService.search(searchDto);
    }

    @PostMapping("/update")
    @ResponseBody
    public UserVo update(@RequestBody UserUpdateDto updateDto) {
        return userService.update(updateDto);
    }

    @PostMapping("/delete")
    @ResponseBody
    public void delete(@RequestBody UserDeleteDto deleteDto) {
        userService.delete(deleteDto);
    }
}
