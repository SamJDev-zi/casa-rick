package com.casarick.api.controller;

import com.casarick.api.dto.ManagerResponseDTO;
import com.casarick.api.dto.ManagerRequestDTO;
import com.casarick.api.service.imp.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/managers")
public class ManagerController {

    @Autowired
    private IManagerService service;

    @GetMapping
    public ResponseEntity<List<ManagerResponseDTO>> getManagers() {
        return ResponseEntity.ok(service.getManagers());
    }

    @GetMapping("/{name}")
    public ResponseEntity<ManagerResponseDTO> getManagerByName(@PathVariable String name) {
        Optional<ManagerResponseDTO> managerOptional = service.getManagerByName(name);

        return managerOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ManagerResponseDTO> createManager(@RequestBody ManagerRequestDTO request) {
        ManagerResponseDTO manager = service.createManager(request);
        return ResponseEntity.created(URI.create("/api/managers" + manager.getName())).body(manager);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManagerResponseDTO> updateManager(@PathVariable Long id, @RequestBody ManagerRequestDTO request) {
        return ResponseEntity.ok(service.updateManager(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManager(@PathVariable Long id) {
        service.deleteManager(id);
        return ResponseEntity.noContent().build();
    }
}
