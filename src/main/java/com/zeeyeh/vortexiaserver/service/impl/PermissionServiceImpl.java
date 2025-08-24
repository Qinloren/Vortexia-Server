package com.zeeyeh.vortexiaserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeeyeh.vortexiaserver.data.dto.PermissionCreateDto;
import com.zeeyeh.vortexiaserver.data.dto.PermissionDeleteDto;
import com.zeeyeh.vortexiaserver.data.dto.PermissionSearchDto;
import com.zeeyeh.vortexiaserver.data.dto.PermissionUpdateDto;
import com.zeeyeh.vortexiaserver.data.entity.ErrorResult;
import com.zeeyeh.vortexiaserver.data.entity.Permission;
import com.zeeyeh.vortexiaserver.data.vo.PermissionSearchVo;
import com.zeeyeh.vortexiaserver.data.vo.PermissionVo;
import com.zeeyeh.vortexiaserver.data.vo.RoleVo;
import com.zeeyeh.vortexiaserver.exception.HttpRequestException;
import com.zeeyeh.vortexiaserver.mapper.PermissionMapper;
import com.zeeyeh.vortexiaserver.service.PermissionService;
import com.zeeyeh.vortexiaserver.utils.BeanUtils;
import jakarta.annotation.Resource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Resource
    PermissionMapper permissionMapper;

    @Override
    public PermissionVo create(PermissionCreateDto createDto) {
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Permission::getName, createDto.getName());
        if (this.count(queryWrapper) > 0) {
            throw new HttpRequestException(ErrorResult.PERMISSION_ALREADY_EXIST);
        }
        Permission permission = new Permission();
        BeanUtils.copyPropertiesIgnoreMissing(createDto, permission);
        try {
            int inserted = permissionMapper.insert(permission);
            if (inserted > 0) {
                PermissionVo permissionVo = new PermissionVo();
                BeanUtils.copyPropertiesIgnoreMissing(permission, permissionVo);
                return permissionVo;
            } else {
                throw new HttpRequestException(ErrorResult.PERMISSION_ALREADY_EXIST);
            }
        } catch (DuplicateKeyException e) {
            throw new HttpRequestException(ErrorResult.PERMISSION_ALREADY_EXIST);
        }
    }

    @Override
    public PermissionVo update(PermissionUpdateDto updateDto) {
        Permission permission = permissionMapper.selectById(updateDto.getId());
        if (permission == null) {
            throw new HttpRequestException(ErrorResult.PERMISSION_NOT_FOUND);
        }
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Permission::getName, updateDto.getName());
        queryWrapper.ne(Permission::getId, updateDto.getId());
        if (this.count(queryWrapper) > 0) {
            throw new HttpRequestException(ErrorResult.PERMISSION_ALREADY_EXIST);
        }
        BeanUtils.copyPropertiesIgnoreMissing(updateDto, permission);
        this.updateById(permission);
        return convertToVo(permission);
    }

    private PermissionVo convertToVo(Permission permission) {
        PermissionVo permissionVo = new PermissionVo();
        BeanUtils.copyPropertiesIgnoreMissing(permission, permissionVo);
        return permissionVo;
    }

    @Override
    public void delete(PermissionDeleteDto deleteDto) {
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Permission::getId, deleteDto.getId());
        if (!permissionMapper.exists(queryWrapper)) {
            throw new HttpRequestException(ErrorResult.PERMISSION_NOT_FOUND);
        }
        permissionMapper.deleteById(deleteDto.getId());
    }

    @Override
    public PermissionSearchVo search(PermissionSearchDto searchDto) {
        Page<Permission> permissionPage = new Page<>(searchDto.getPage(), searchDto.getSize());
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(searchDto.getName())) {
            queryWrapper.like(Permission::getName, searchDto.getName());
        }
        if (StringUtils.hasText(searchDto.getDescription())) {
            queryWrapper.like(Permission::getDescription, searchDto.getDescription());
        }
        if (StringUtils.hasText(searchDto.getGroup())) {
            queryWrapper.like(Permission::getGroup, searchDto.getGroup());
        }
        Page<Permission> page = this.page(permissionPage, queryWrapper);
        List<PermissionVo> permissionVos = page.getRecords().stream()
                .map(this::convertToVo)
                .toList();
        return new PermissionSearchVo(
                permissionVos,
                page.getTotal(),
                (int) page.getCurrent(),
                (int) page.getSize()
        );
    }
}
