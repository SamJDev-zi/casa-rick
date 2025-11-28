package com.casarick.api.service.imp;

import com.casarick.api.dto.BranchRequestDTO;
import com.casarick.api.dto.BranchResponseDTO;
import com.casarick.api.model.Branch;

import java.util.List;

public interface IBranchService {
    List<BranchResponseDTO> getBranches();
    BranchResponseDTO getBranchByID(Long id);
    BranchResponseDTO createBranches(BranchRequestDTO branch);
    BranchResponseDTO updateBranch(Long id, BranchRequestDTO branchRequestDTO);
    void deleteBranch(Long id);
}
