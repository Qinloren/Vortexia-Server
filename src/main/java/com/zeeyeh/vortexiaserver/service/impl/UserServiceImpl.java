package com.zeeyeh.vortexiaserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeeyeh.vortexiaserver.data.dto.*;
import com.zeeyeh.vortexiaserver.data.entity.ErrorResult;
import com.zeeyeh.vortexiaserver.data.entity.Result;
import com.zeeyeh.vortexiaserver.data.entity.User;
import com.zeeyeh.vortexiaserver.data.entity.UserStatus;
import com.zeeyeh.vortexiaserver.data.vo.UserSearchVo;
import com.zeeyeh.vortexiaserver.data.vo.UserVo;
import com.zeeyeh.vortexiaserver.exception.HttpRequestException;
import com.zeeyeh.vortexiaserver.mapper.UserMapper;
import com.zeeyeh.vortexiaserver.provider.PasswordEncoder;
import com.zeeyeh.vortexiaserver.provider.RedisProvider;
import com.zeeyeh.vortexiaserver.provider.TokenProvider;
import com.zeeyeh.vortexiaserver.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    public static final Pattern userIdPattern = Pattern.compile("^[1-9]\\d*$");
    public static final Pattern usernamePattern = Pattern.compile("^[a-zA-Z0-9_-]{4,16}$");
    public static final Pattern passwordPattern = Pattern.compile("^(?=(.*[A-Z]){2,})(?=(.*[a-z]){2,}).{8,}$");
    public static final Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");

    @Resource
    private UserMapper userMapper;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private TokenProvider tokenProvider;
    @Resource
    private RedisProvider redisProvider;

    @Override
    public UserVo create(UserCreateDto userCreateDto) {
        if (checkUserExist(userCreateDto.getId(), userCreateDto.getUsername(), userCreateDto.getEmail())) {
            throw new RuntimeException("用户已存在");
        }
        User user = new User();
        BeanUtils.copyProperties(userCreateDto, user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(UserStatus.VERIFYING);
        user.setUpdateTime(System.currentTimeMillis());
        user.setCreateTime(System.currentTimeMillis());
        int inserted = userMapper.insert(user);
        if (inserted > 0) {
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(user, userVo);
            return userVo;
        } else {
            throw new RuntimeException("用户创建失败");
        }
    }

    private boolean checkUserExist(Long id, String username, String email) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        boolean hasCondition = false;

        if (id != null) {
            queryWrapper.eq(User::getId, id);
            hasCondition = true;
        }
        if (username != null) {
            queryWrapper.or().eq(User::getUsername, username);
            hasCondition = true;
        }
        if (email != null) {
            queryWrapper.or().eq(User::getEmail, email);
            hasCondition = true;
        }

        return hasCondition && userMapper.selectCount(queryWrapper) > 0;
    }


    @Override
    public Result<UserVo> login(UserLoginDto userLoginDto) {
        String account = userLoginDto.getAccount();
        String password = userLoginDto.getPassword();
        User user;
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (userIdPattern.matcher(account).matches()) {
            user = userMapper.selectById(Long.valueOf(account));
        } else if (usernamePattern.matcher(account).matches()) {
            user = userMapper.selectOne(queryWrapper.eq(User::getUsername, account));
        } else if (emailPattern.matcher(account).matches()) {
            user = userMapper.selectOne(queryWrapper.eq(User::getEmail, account));
        } else {
            throw new HttpRequestException(ErrorResult.INVALID_USER_INFO);
        }
        if (user == null) {
            throw new HttpRequestException(ErrorResult.USER_NOT_FOUND);
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new HttpRequestException(ErrorResult.PASSWORD_ERROR);
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        String token = "Bearer " + tokenProvider.createToken(Map.of(
                "id", user.getId(),
                "username", user.getUsername(),
                "email", user.getEmail()
                ));
        redisProvider.set("token:user:" + user.getId(), token);
        return Result.success(userVo, Map.of(
                "Authorization",
                token
        ));
    }

    @Override
    public UserSearchVo search(UserSearchDto searchDto) {
        Page<User> userPage = new Page<>(searchDto.getPage(), searchDto.getSize());
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(searchDto.getUsername())) {
            queryWrapper.like(User::getUsername, searchDto.getUsername());
        }
        if (StringUtils.hasText(searchDto.getNickname())) {
            queryWrapper.like(User::getNickname, searchDto.getNickname());
        }
        if (StringUtils.hasText(searchDto.getEmail())) {
            queryWrapper.like(User::getEmail, searchDto.getEmail());
        }
        if (searchDto.getStatus() != null) {
            queryWrapper.eq(User::getStatus, searchDto.getStatus());
        }
        if (searchDto.getRoleId() != null) {
            queryWrapper.eq(User::getRole, searchDto.getRoleId());
        }
        Page<User> page = this.page(userPage, queryWrapper);
        List<UserVo> userVos = page.getRecords().stream()
                .map(this::convertToVo)
                .collect(Collectors.toList());
        return new UserSearchVo(
                userVos,
                page.getTotal(),
                (int) page.getCurrent(),
                (int) page.getSize()
        );
    }

    private UserVo convertToVo(User user) {
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }

    @Override
    public UserVo update(UserUpdateDto updateDto) {
        User user = userMapper.selectById(updateDto.getId());
        if (user == null) {
            throw new HttpRequestException(ErrorResult.USER_NOT_FOUND);
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, updateDto.getUsername());
        queryWrapper.ne(User::getId, updateDto.getId());
        if (this.count(queryWrapper) > 0) {
            throw new HttpRequestException(ErrorResult.USERNAME_ALREADY_EXIST);
        }

        BeanUtils.copyProperties(updateDto, user);
        this.updateById(user);
        return convertToVo(user);
    }

    @Override
    public void delete(UserDeleteDto deleteDto) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, deleteDto.getId());
        if (!userMapper.exists(queryWrapper)) {
            throw new HttpRequestException(ErrorResult.USER_NOT_FOUND);
        }
        userMapper.deleteById(deleteDto.getId());
    }
}
