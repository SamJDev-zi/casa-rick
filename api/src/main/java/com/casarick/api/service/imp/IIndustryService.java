package com.casarick.api.service.imp;

import com.casarick.api.dto.IndustryDTO;
import com.casarick.api.model.Industry;

import java.util.List;

public interface IIndustryService {
    List<IndustryDTO> getIndustries();
    IndustryDTO getIndustryByNId(Long id);
    IndustryDTO createIndustry(Industry industry);
    IndustryDTO updateIndustry(Long id, IndustryDTO industryDTO);
}
