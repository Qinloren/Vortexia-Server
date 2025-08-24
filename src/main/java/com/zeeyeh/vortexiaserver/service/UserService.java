package com.zeeyeh.vortexiaserver.service;

import com.zeeyeh.vortexiaserver.data.dto.*;
import com.zeeyeh.vortexiaserver.data.entity.Result;
import com.zeeyeh.vortexiaserver.data.vo.UserSearchVo;
import com.zeeyeh.vortexiaserver.data.vo.UserVo;


public interface UserService {

    /**
     * 注册用户
     * @param registerDto 注册信息
     * @return 用户信息
     */
    UserVo register(UserRegisterDto registerDto);
    /**
     * 创建用户
     * @param userCreateDto 用户创建信息
     * @return 用户信息
     */
    UserVo create(UserCreateDto userCreateDto);

    /**
     * 用户登录
     *
     * @param userLoginDto 用户登录信息
     * @return 用户信息
     */
    Result<UserVo> login(UserLoginDto userLoginDto);

    /**
     * 用户搜索
     * @param searchDto 用户搜索信息
     * @return 用户信息列表
     */
    UserSearchVo search(UserSearchDto searchDto);

    /**
     * 更新用户信息
     * @param updateDto 用户更新信息
     * @return 用户信息
     */
    UserVo update(UserUpdateDto updateDto);

    /**
     * 删除用户
     * @param deleteDto 用户删除信息
     */
    void delete(UserDeleteDto deleteDto);
}
