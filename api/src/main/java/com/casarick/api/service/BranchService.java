package com.casarick.api.service;

import com.casarick.api.dto.BranchRequestDTO;
import com.casarick.api.dto.BranchResponseDTO;
import com.casarick.api.exception.NotFoundException;
import com.casarick.api.mapper.Mapper;
import com.casarick.api.model.Branch;
import com.casarick.api.model.Manager;
import com.casarick.api.reposiroty.BranchRepository;
import com.casarick.api.reposiroty.ManagerRepository;
import com.casarick.api.service.imp.IBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchService implements IBranchService {

    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private ManagerRepository managerRepository;

    @Override
    public List<BranchResponseDTO> getBranches() {
        return branchRepository.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public BranchResponseDTO getBranchByID(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Branch not found with ny id: " + id));

        return Mapper.toDTO(branch);
    }

    @Override
    public BranchResponseDTO createBranches(BranchRequestDTO branchDTO) {
        if (branchDTO == null) {
            throw new IllegalArgumentException("Branch object cannot be null for creation.");
        }

        Manager manager = managerRepository.findById(branchDTO.getManagerId())
                .orElseThrow(() -> new NotFoundException("Manager not found with ID: " + branchDTO.getManagerId()));

        Branch branch = Mapper.toEntity(branchDTO, manager);
        branch.setActive(true);

        return Mapper.toDTO(branchRepository.save(branch));
    }

    @Override
    public BranchResponseDTO updateBranch(Long id, BranchRequestDTO branchRequestDTO) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("branch not found with id: " + id));

        Manager manager = managerRepository.findById(branchRequestDTO.getManagerId())
                .orElseThrow(() -> new NotFoundException("Manager not found with ID: " + branchRequestDTO.getManagerId()));

        branch = Mapper.toEntity(branchRequestDTO, manager);
        branch.setActive(true);

        return Mapper.toDTO(branchRepository.save(branch));
    }

    @Override
    public void deleteBranch(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Branch not found by ID: " + id));

        branchRepository.delete(branch);
    }
}
