package com.casarick.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductVariantDTO {
    private Long id;
    private CategoryDTO categoryDTO;
    private ClotheTypeDTO clotheTypeDTO;
    private IndustryDTO industryDTO;
    private String color;
    private String size;
}
