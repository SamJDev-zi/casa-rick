package com.casarick.api.controller;

import com.casarick.api.dto.BranchRequestDTO;
import com.casarick.api.dto.BranchResponseDTO;
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
    public ResponseEntity<List<BranchResponseDTO>> getBranches() {
        return ResponseEntity.ok(service.getBranches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchResponseDTO> getBranchById(@PathVariable Long id) {
        BranchResponseDTO BranchResponseDTO = service.getBranchByID(id);
        return ResponseEntity.ok(BranchResponseDTO);
    }

    @PostMapping()
    public ResponseEntity<BranchResponseDTO> createBranch(@RequestBody BranchRequestDTO branch) {
        BranchResponseDTO BranchResponseDTO = service.createBranches(branch);
        return ResponseEntity.created(URI.create("/api/branches/" + BranchResponseDTO.getId())).body(BranchResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BranchResponseDTO> updateBranch(
            @PathVariable Long id,
            @RequestBody BranchRequestDTO branchRequestDTO) {

        BranchResponseDTO updatedBranch = service.updateBranch(id, branchRequestDTO);

        return ResponseEntity.ok(updatedBranch);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
        service.deleteBranch(id);
        return ResponseEntity.noContent().build();
    }
}
