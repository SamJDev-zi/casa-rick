package com.casarick.api.service.imp;

import com.casarick.api.dto.PermissionDTO;
import com.casarick.api.model.Permission;

import java.util.List;

public interface IPermissionService {
    List<PermissionDTO> getPermissions();
    PermissionDTO getPermissionById(Long id);
    PermissionDTO createPermission(Permission permission);
    PermissionDTO updatePermission(Long id, PermissionDTO permissionDTO);
    void deletePermission(Long id);
}
