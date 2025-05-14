package com.example.bankcards.controller;

import com.example.bankcards.dto.RoleDTO;
import com.example.bankcards.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
@Tag(name = "Роли", description = "Управление ролями пользователей")
public class RoleController {
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    @Operation(summary = "Создать новую роль")
    public ResponseEntity<RoleDTO.RoleResponse> createRole(@Valid @RequestBody RoleDTO.RoleRequest request) {
        RoleDTO.RoleResponse response = roleService.createRole(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить роль по ID")
    public ResponseEntity<RoleDTO.RoleResponse> getRole(@PathVariable Long id) throws RoleNotFoundException {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @GetMapping
    @Operation(summary = "Получить все роли")
    public ResponseEntity<List<RoleDTO.RoleResponse>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить роль")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) throws RoleNotFoundException {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
