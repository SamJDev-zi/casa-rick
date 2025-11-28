package com.casarick.api.service;

import com.casarick.api.dto.ClotheTypeDTO;
import com.casarick.api.exception.NotFoundException;
import com.casarick.api.mapper.Mapper;
import com.casarick.api.model.ClotheType;
import com.casarick.api.reposiroty.ClotheTypeRepository;
import com.casarick.api.service.imp.IClotheTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClotheTypeService implements IClotheTypeService {

    @Autowired
    private ClotheTypeRepository repository;

    @Override
    public List<ClotheTypeDTO> getClotheTypes() {
        return repository.findAll().stream().map(Mapper::toDTO).toList();
    }

    @Override
    public ClotheTypeDTO getClothTypeById(Long id) {
        ClotheType type = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Clothe type not found with id: " +id));

        return Mapper.toDTO(type);
    }

    @Override
    public Optional<ClotheTypeDTO> getClotheTypeByName(String name) {
        Optional<ClotheType> clotheTypeOptional = repository.getClotheTypeByName(name);
        return clotheTypeOptional.map(Mapper::toDTO);
    }

    @Override
    public ClotheTypeDTO createClotheType(ClotheType clotheType) {
        if (clotheType == null) {
            throw new IllegalArgumentException("ClotheType object cannot be null for creation.");
        }
        return Mapper.toDTO(repository.save(clotheType));
    }

    @Override
    public ClotheTypeDTO updateClotheType(Long id, ClotheTypeDTO clotheTypeDTO) {
        ClotheType type = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Clothe type not found"));

        type.setName(clotheTypeDTO.getName());
        return Mapper.toDTO(repository.save(type));
    }
}
