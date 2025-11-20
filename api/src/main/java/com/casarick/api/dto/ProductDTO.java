package com.casarick.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private ProductVariantDTO productVariantDTO;
    private String photoURL;
    private String barCode;
    private BranchDTO branchDTO;
}
