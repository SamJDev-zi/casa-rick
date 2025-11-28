package com.casarick.api.service.imp;

import com.casarick.api.dto.ManagerResponseDTO;
import com.casarick.api.dto.ManagerRequestDTO;

import java.util.List;
import java.util.Optional;

public interface IManagerService {
    List<ManagerResponseDTO> getManagers();
    Optional<ManagerResponseDTO> getManagerByName(String name);
    ManagerResponseDTO createManager(ManagerRequestDTO requestDTO);
    ManagerResponseDTO updateManager(Long id, ManagerRequestDTO requestDTO);
    void deleteManager(Long id);
}
