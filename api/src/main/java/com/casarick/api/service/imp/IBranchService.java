package com.casarick.api.service.imp;

import com.casarick.api.dto.BranchDTO;
import com.casarick.api.dto.ManagerDTO;
import com.casarick.api.model.Branch;

import java.util.List;

public interface IBranchService {
    List<BranchDTO> getBranches();
    BranchDTO getBranchByID(Long id);
    BranchDTO createBranches(Branch branch, Long managerID);
    BranchDTO updateBranch(Long id, BranchDTO branchDTO, Long managerId);
    void deleteBranch(Long id);
}
