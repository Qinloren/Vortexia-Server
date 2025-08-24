package com.zeeyeh.vortexiaserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zeeyeh.vortexiaserver.data.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
