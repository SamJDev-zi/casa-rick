package com.casarick.api.reposiroty;

import com.casarick.api.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
