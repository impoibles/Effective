package com.example.bankcards.mapper;

import com.example.bankcards.dto.RoleDTO;
import com.example.bankcards.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toEntity(RoleDTO.RoleRequest request);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    RoleDTO.RoleResponse toResponse(Role role);
}
