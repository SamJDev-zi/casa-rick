package com.casarick.api.controller;

import com.casarick.api.dto.BranchDTO;
import com.casarick.api.model.Branch;
import com.casarick.api.service.imp.IBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/branches")
public class BranchController {

    @Autowired
    private IBranchService service;

    @GetMapping
    public ResponseEntity<List<BranchDTO>> getBranches() {
        return ResponseEntity.ok(service.getBranches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDTO> getBranchById(@PathVariable Long id) {
        BranchDTO branchDTO = service.getBranchByID(id);
        return ResponseEntity.ok(branchDTO);
    }

    @PostMapping("/manager/{managerId}")
    public ResponseEntity<BranchDTO> createBranch(@PathVariable Long managerId,
                                                  @RequestBody Branch branch) {

        BranchDTO branchDTO = service.createBranches(branch, managerId);
        return ResponseEntity.created(URI.create("/api/branches/" + branchDTO.getId())).body(branchDTO);
    }

    @PutMapping("/{id}/manager/{managerId}")
    public ResponseEntity<BranchDTO> updateBranch(
            @PathVariable Long id,
            @PathVariable Long managerId,
            @RequestBody BranchDTO branchDTO) {

        BranchDTO updatedBranch = service.updateBranch(id, branchDTO, managerId);

        return ResponseEntity.ok(updatedBranch);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        service.deleteBranch(id);
        return ResponseEntity.noContent().build();
    }
}
