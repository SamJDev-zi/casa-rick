package com.casarick.api.service.imp;

import com.casarick.api.dto.ClotheTypeDTO;
import com.casarick.api.model.ClotheType;

import java.util.List;
import java.util.Optional;

public interface IClotheTypeService {
    List<ClotheTypeDTO> getClotheTypes();
    ClotheTypeDTO getClothTypeById(Long id);
    Optional<ClotheTypeDTO> getClotheTypeByName(String name);
    ClotheTypeDTO createClotheType(ClotheType clotheType);
    ClotheTypeDTO updateClotheType(Long id, ClotheTypeDTO clotheTypeDTO);
}
