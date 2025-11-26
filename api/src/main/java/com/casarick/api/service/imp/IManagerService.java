package com.casarick.api.service.imp;

import com.casarick.api.dto.ManagerDTO;
import com.casarick.api.dto.ManagerRequestDTO;
import com.casarick.api.model.Manager;

import java.util.List;
import java.util.Optional;

public interface IManagerService {
    List<ManagerDTO> getManagers();
    Optional<ManagerDTO> getManagerByName(String name);
    ManagerDTO createManager(ManagerRequestDTO requestDTO);
    ManagerDTO updateManager(Long id, ManagerRequestDTO requestDTO);
    void deleteManager(Long id);
}
