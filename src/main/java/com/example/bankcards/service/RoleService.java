package com.example.bankcards.service;


import com.example.bankcards.dto.RoleDTO;
import com.example.bankcards.entity.Card;
import com.example.bankcards.entity.Role;
import com.example.bankcards.exception.CardNotFoundException;
import com.example.bankcards.exception.RoleAlreadyExistsException;
import com.example.bankcards.mapper.RoleMapper;
import com.example.bankcards.repository.RoleRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private final RoleRepo roleRepository;
    private final RoleMapper roleMapper;

    @Autowired
    public RoleService(RoleRepo roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    // Создание роли
    @Transactional
    public RoleDTO.RoleResponse createRole(RoleDTO.RoleRequest request) {
        if (roleRepository.existsByName(request.getName())) {
            throw new RoleAlreadyExistsException("Роль '" + request.getName() + "' уже существует");
        }

        Role role = roleMapper.toEntity(request);
        Role savedRole = roleRepository.save(role);
        return roleMapper.toResponse(savedRole);
    }

    // Получение роли по ID
    @Transactional()
    public RoleDTO.RoleResponse getRoleById(Long id) throws RoleNotFoundException {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Роль не найдена"));
        return roleMapper.toResponse(role);
    }

    // Получение всех ролей
    @Transactional()
    public List<RoleDTO.RoleResponse> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Удаление роли
    @Transactional
    public void deleteRole(Long id) throws RoleNotFoundException {
        if (!roleRepository.existsById(id)) {
            throw new RoleNotFoundException("Роль не найдена");
        }
        roleRepository.deleteById(id);
    }
}
