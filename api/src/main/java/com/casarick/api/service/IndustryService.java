package com.casarick.api.service;

import com.casarick.api.dto.IndustryDTO;
import com.casarick.api.exception.NotFoundException;
import com.casarick.api.mapper.Mapper;
import com.casarick.api.model.Industry;
import com.casarick.api.reposiroty.IndustryRepository;
import com.casarick.api.service.imp.IIndustryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndustryService implements IIndustryService {

    @Autowired
    private IndustryRepository repository;

    @Override
    public List<IndustryDTO> getIndustries() {
        return repository.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public IndustryDTO getIndustryByNId(Long id) {
        Industry industry = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Industry not found"));
        return Mapper.toDTO(industry);
    }

    @Override
    public IndustryDTO createIndustry(Industry industry) {
        return Mapper.toDTO(repository.save(industry));
    }

    @Override
    public IndustryDTO updateIndustry(Long id, IndustryDTO industryDTO) {
        Industry industry = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Industry not found"));

        industry.setName(industryDTO.getName());
        return Mapper.toDTO(repository.save(industry));
    }
}
