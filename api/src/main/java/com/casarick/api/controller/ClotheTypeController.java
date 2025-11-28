package com.casarick.api.controller;

import com.casarick.api.dto.ClotheTypeDTO;
import com.casarick.api.model.ClotheType;
import com.casarick.api.service.imp.IClotheTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/types")
public class ClotheTypeController {

    @Autowired
    private IClotheTypeService service;

    @GetMapping
    public ResponseEntity<List<ClotheTypeDTO>> getClotheTypes() {
        return ResponseEntity.ok(service.getClotheTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClotheTypeDTO> getClotheTypeById(@PathVariable Long id) {

        ClotheTypeDTO clotheTypeDTO = service.getClothTypeById(id);

        return ResponseEntity.ok(clotheTypeDTO);
    }

    @GetMapping("/{name}")
    public ResponseEntity<ClotheTypeDTO> getClotheTypeByName(@PathVariable String name) {
        Optional<ClotheTypeDTO> typeOptional = service.getClotheTypeByName(name);

        return typeOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClotheTypeDTO> createClotheType(@RequestBody ClotheType type) {
        ClotheTypeDTO typeDTO = service.createClotheType(type);

        return ResponseEntity.created(URI.create("/api/types" + typeDTO.getName())).body(typeDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClotheTypeDTO> updateClotheType(@PathVariable Long id, @RequestBody ClotheTypeDTO clotheTypeDTO) {
        return ResponseEntity.ok(service.updateClotheType(id, clotheTypeDTO));
    }
}
