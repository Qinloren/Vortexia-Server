package com.zeeyeh.vortexiaserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeeyeh.vortexiaserver.data.dto.RolePermissionCreateDto;
import com.zeeyeh.vortexiaserver.data.dto.RolePermissionDeleteDto;
import com.zeeyeh.vortexiaserver.data.dto.RolePermissionSearchDto;
import com.zeeyeh.vortexiaserver.data.dto.RolePermissionUpdateDto;
import com.zeeyeh.vortexiaserver.data.entity.ErrorResult;
import com.zeeyeh.vortexiaserver.data.entity.RolePermission;
import com.zeeyeh.vortexiaserver.data.vo.RolePermissionSearchVo;
import com.zeeyeh.vortexiaserver.data.vo.RolePermissionVo;
import com.zeeyeh.vortexiaserver.exception.HttpRequestException;
import com.zeeyeh.vortexiaserver.mapper.RolePermissionMapper;
import com.zeeyeh.vortexiaserver.service.RolePermissionService;
import com.zeeyeh.vortexiaserver.utils.BeanUtils;
import jakarta.annotation.Resource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {
    @Resource
    RolePermissionMapper rolePermissionMapper;

    @Override
    public RolePermissionVo create(RolePermissionCreateDto createDto) {
        LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RolePermission::getRoleId, createDto.getRoleId());
        if (this.count(queryWrapper) > 0) {
            throw new HttpRequestException(ErrorResult.ROLE_PERMISSION_ALREADY_EXIST);
        }
        RolePermission rolePermission = new RolePermission();
        BeanUtils.copyPropertiesIgnoreMissing(createDto, rolePermission);
        try {
            int inserted = rolePermissionMapper.insert(rolePermission);
            if (inserted > 0) {
                RolePermissionVo rolePermissionVo = new RolePermissionVo();
                BeanUtils.copyPropertiesIgnoreMissing(rolePermission, rolePermissionVo);
                return rolePermissionVo;
            } else {
                throw new HttpRequestException(ErrorResult.ROLE_PERMISSION_ALREADY_EXIST);
            }
        } catch (DuplicateKeyException e) {
            throw new HttpRequestException(ErrorResult.ROLE_PERMISSION_ALREADY_EXIST);
        }
    }

    @Override
    public RolePermissionVo update(RolePermissionUpdateDto updateDto) {
        RolePermission rolePermission = rolePermissionMapper.selectById(updateDto.getId());
        if (rolePermission == null) {
            throw new HttpRequestException(ErrorResult.ROLE_PERMISSION_NOT_FOUND);
        }
        LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RolePermission::getRoleId, updateDto.getRoleId());
        queryWrapper.ne(RolePermission::getId, updateDto.getId());
        if (this.count(queryWrapper) > 0) {
            throw new HttpRequestException(ErrorResult.ROLE_PERMISSION_ALREADY_EXIST);
        }
        BeanUtils.copyPropertiesIgnoreMissing(updateDto, rolePermission);
        this.updateById(rolePermission);
        return convertToVo(rolePermission);
    }

    private RolePermissionVo convertToVo(RolePermission rolePermission) {
        RolePermissionVo rolePermissionVo = new RolePermissionVo();
        BeanUtils.copyPropertiesIgnoreMissing(rolePermission, rolePermissionVo);
        return rolePermissionVo;
    }

    @Override
    public void delete(RolePermissionDeleteDto deleteDto) {
        LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RolePermission::getId, deleteDto.getId());
        if (!rolePermissionMapper.exists(queryWrapper)) {
            throw new HttpRequestException(ErrorResult.ROLE_PERMISSION_NOT_FOUND);
        }
        rolePermissionMapper.deleteById(deleteDto.getId());
    }

    @Override
    public RolePermissionSearchVo search(RolePermissionSearchDto searchDto) {
        Page<RolePermission> rolePermissionPage = new Page<>(searchDto.getPage(), searchDto.getSize());
        LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<>();
        if (searchDto.getRoleId() != null) {
            queryWrapper.eq(RolePermission::getRoleId, searchDto.getRoleId());
        }
        Page<RolePermission> page = this.page(rolePermissionPage, queryWrapper);
        List<RolePermissionVo> permissionVos = page.getRecords().stream()
                .map(this::convertToVo)
                .toList();
        return new RolePermissionSearchVo(
                permissionVos,
                page.getTotal(),
                (int) page.getCurrent(),
                (int) page.getSize()
        );
    }
}
