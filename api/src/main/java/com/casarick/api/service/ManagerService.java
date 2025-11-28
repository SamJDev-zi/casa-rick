package com.casarick.api.service;

import com.casarick.api.dto.ManagerResponseDTO;
import com.casarick.api.dto.ManagerRequestDTO;
import com.casarick.api.exception.NotFoundException;
import com.casarick.api.mapper.Mapper;
import com.casarick.api.model.Manager;
import com.casarick.api.reposiroty.ManagerRepository;
import com.casarick.api.service.imp.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManagerService implements IManagerService {

    @Autowired
    private ManagerRepository repository;

    @Override
    public List<ManagerResponseDTO> getManagers() {
        return repository.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public Optional<ManagerResponseDTO> getManagerByName(String name) {
        Optional<Manager> managerOptional = repository.findByName(name);
        return managerOptional.map(Mapper::toDTO);
    }

    @Override
    public ManagerResponseDTO createManager(ManagerRequestDTO requestDTO) {
        Manager manager = Mapper.toEntity(requestDTO);
        return Mapper.toDTO(repository.save(manager));
    }

    @Override
    public ManagerResponseDTO updateManager(Long id, ManagerRequestDTO requestDTO) {
        Manager manager = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Manager not found"));

        manager.setName(requestDTO.getName());
        manager.setLastName(requestDTO.getLastName());
        manager.setPassword(requestDTO.getPassword());
        return Mapper.toDTO(repository.save(manager));
    }

    @Override
    public void deleteManager(Long id) {
        Manager manager = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Manager not found by id: " + id));
        repository.delete(manager);
    }
}
