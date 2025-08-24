package com.zeeyeh.vortexiaserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeeyeh.vortexiaserver.data.entity.RolePermission;
import com.zeeyeh.vortexiaserver.mapper.RolePermissionMapper;
import com.zeeyeh.vortexiaserver.service.PermissionService;
import com.zeeyeh.vortexiaserver.service.RolePermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {
}
