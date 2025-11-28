package com.casarick.api.service.imp;

import com.casarick.api.dto.SaleRequestDTO;
import com.casarick.api.dto.SaleResponseDTO;

import java.util.List;

public interface ISaleService {
    List<SaleResponseDTO> getSales();
    SaleResponseDTO getSaleById(Long id);
    SaleResponseDTO createSale(SaleRequestDTO saleResponseDTO);
    SaleResponseDTO updateSale(Long id, SaleResponseDTO saleResponseDTO);
}
