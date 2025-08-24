package com.zeeyeh.vortexiaserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zeeyeh.vortexiaserver.data.dto.RoleCreateDto;
import com.zeeyeh.vortexiaserver.data.dto.RoleDeleteDto;
import com.zeeyeh.vortexiaserver.data.dto.RoleSearchDto;
import com.zeeyeh.vortexiaserver.data.dto.RoleUpdateDto;
import com.zeeyeh.vortexiaserver.data.entity.ErrorResult;
import com.zeeyeh.vortexiaserver.data.entity.Role;
import com.zeeyeh.vortexiaserver.data.vo.RoleSearchVo;
import com.zeeyeh.vortexiaserver.data.vo.RoleVo;
import com.zeeyeh.vortexiaserver.exception.HttpRequestException;
import com.zeeyeh.vortexiaserver.mapper.RoleMapper;
import com.zeeyeh.vortexiaserver.service.RoleService;
import com.zeeyeh.vortexiaserver.utils.BeanUtils;
import jakarta.annotation.Resource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    RoleMapper roleMapper;

    @Override
    public RoleVo create(RoleCreateDto createDto) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getName, createDto.getName());
        if (this.count(queryWrapper) > 0) {
            throw new HttpRequestException(ErrorResult.ROLE_ALREADY_EXIST);
        }
        Role role = new Role();
        BeanUtils.copyPropertiesIgnoreMissing(createDto, role);
        role.setUpdateTime(LocalDateTime.now());
        role.setCreateTime(LocalDateTime.now());
        try {
            int inserted = roleMapper.insert(role);
            if (inserted > 0) {
                RoleVo roleVo = new RoleVo();
                BeanUtils.copyPropertiesIgnoreMissing(role, roleVo);
                return roleVo;
            } else {
                throw new HttpRequestException(ErrorResult.ROLE_CREATE_FAILED);
            }
        } catch (DuplicateKeyException e) {
            throw new HttpRequestException(ErrorResult.ROLE_ALREADY_EXIST);
        }
    }

    @Override
    public RoleVo update(RoleUpdateDto updateDto) {
        Role role = roleMapper.selectById(updateDto.getId());
        if (role == null) {
            throw new HttpRequestException(ErrorResult.ROLE_NOT_FOUND);
        }
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getName, updateDto.getName());
        queryWrapper.ne(Role::getId, updateDto.getId());
        if (this.count(queryWrapper) > 0) {
            throw new HttpRequestException(ErrorResult.ROLE_ALREADY_EXIST);
        }
        role.setUpdateTime(LocalDateTime.now());
        BeanUtils.copyPropertiesIgnoreMissing(updateDto, role);
        this.updateById(role);
        return convertToVo(role);
    }

    private RoleVo convertToVo(Role role) {
        RoleVo roleVo = new RoleVo();
        BeanUtils.copyPropertiesIgnoreMissing(role, roleVo);
        return roleVo;
    }

    @Override
    public void delete(RoleDeleteDto deleteDto) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Role::getId, deleteDto.getId());
        if (!roleMapper.exists(queryWrapper)) {
            throw new HttpRequestException(ErrorResult.ROLE_NOT_FOUND);
        }
        roleMapper.deleteById(deleteDto.getId());
    }

    @Override
    public RoleSearchVo search(RoleSearchDto searchDto) {
        Page<Role> rolePage = new Page<>(searchDto.getPage(), searchDto.getSize());
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(searchDto.getName())) {
            queryWrapper.like(Role::getName, searchDto.getName());
        }
        if (StringUtils.hasText(searchDto.getDisplayName())) {
            queryWrapper.like(Role::getDisplayName, searchDto.getDisplayName());
        }
        if (StringUtils.hasText(searchDto.getDescription())) {
            queryWrapper.like(Role::getDescription, searchDto.getDescription());
        }
        Page<Role> page = this.page(rolePage, queryWrapper);
        List<RoleVo> roleVos = page.getRecords().stream()
                .map(this::convertToVo)
                .collect(Collectors.toList());
        return new RoleSearchVo(
                roleVos,
                page.getTotal(),
                (int) page.getCurrent(),
                (int) page.getSize()
        );
    }
}
