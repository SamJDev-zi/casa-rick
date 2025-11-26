package com.casarick.api.service;

import com.casarick.api.dto.PermissionDTO;
import com.casarick.api.exception.NotFoundException;
import com.casarick.api.mapper.Mapper;
import com.casarick.api.model.Permission;
import com.casarick.api.reposiroty.PermissionRepository;
import com.casarick.api.service.imp.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService implements IPermissionService {

    @Autowired
    private PermissionRepository repository;

    @Override
    public List<PermissionDTO> getPermissions() {
        return repository.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public PermissionDTO getPermissionById(Long id) {
        Permission permission = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Permission not found with ID: " + id));
        return Mapper.toDTO(permission);
    }

    @Override
    public PermissionDTO createPermission(Permission permission) {
        return Mapper.toDTO(repository.save(permission));
    }

    @Override
    public PermissionDTO updatePermission(Long id, PermissionDTO permissionDTO) {
        Permission permission = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Permission not found with ID: " + id));

        permission.setName(permission.getName());
        return Mapper.toDTO(repository.save(permission));
    }

    @Override
    public void deletePermission(Long id) {
        Permission permission = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Permission not found with ID: " + id));

        repository.delete(permission);
    }
}
