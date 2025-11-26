package com.casarick.api.service;

import com.casarick.api.dto.BranchDTO;
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
    public List<BranchDTO> getBranches() {
        return branchRepository.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public BranchDTO getBranchByID(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Branch not found"));

        return Mapper.toDTO(branch);
    }

    @Override
    public BranchDTO createBranches(Branch branch, Long managerID) {
        if (branch == null) {
            throw new IllegalArgumentException("Branch object cannot be null for creation.");
        }

        Manager manager = managerRepository.findById(managerID)
                .orElseThrow(() -> new NotFoundException("Manager not found with ID: " + managerID));

        branch.setManager(manager);
        branch.setActive(true);

        return Mapper.toDTO(branchRepository.save(branch));
    }

    @Override
    public BranchDTO updateBranch(Long id, BranchDTO branchDTO, Long managerId) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("branch not found"));

        branch.setName(branchDTO.getName());
        branch.setAddress(branchDTO.getAddress());
        branch.setPhoneNumber(branchDTO.getPhoneNumber());
        branch.setActive(branchDTO.isActive());

        Manager manager = managerRepository.findById(managerId)
                .orElseThrow(() -> new NotFoundException("Manager not found with ID: " + managerId));

        branch.setManager(manager);

        return Mapper.toDTO(branchRepository.save(branch));
    }

    @Override
    public void deleteBranch(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Branch not found by ID: " + id));

        branchRepository.delete(branch);
    }
}
