package com.casarick.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequestDTO {
    private Long id;
    private String name;
    private Long categoryId;
    private Long clotheTypeId;
    private Long industryId;
    private String color;
    private String size;
    private String photoURL;
    private String barCode;
}
