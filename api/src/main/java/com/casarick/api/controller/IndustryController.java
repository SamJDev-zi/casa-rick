package com.casarick.api.controller;

import com.casarick.api.dto.IndustryDTO;
import com.casarick.api.service.IndustryService;
import com.casarick.api.model.Industry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/industries")
public class IndustryController {

    @Autowired
    private IndustryService industryService;

    @GetMapping
    public ResponseEntity<List<IndustryDTO>> getAllIndustries() {
        return ResponseEntity.ok(industryService.getIndustries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IndustryDTO> getIndustryById(@PathVariable Long id) {
        return ResponseEntity.ok(industryService.getIndustryByNId(id));
    }

    @PostMapping
    public ResponseEntity<IndustryDTO> createIndustry(@RequestBody Industry industry) {
        IndustryDTO createdDTO = industryService.createIndustry(industry);
        return ResponseEntity.created(URI.create("/api/industries" + createdDTO.getName())).body(createdDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IndustryDTO> updateIndustry(@PathVariable Long id, @RequestBody IndustryDTO industryDTO) {
        IndustryDTO updatedDTO = industryService.updateIndustry(id, industryDTO);
        return ResponseEntity.ok(updatedDTO);
    }
}