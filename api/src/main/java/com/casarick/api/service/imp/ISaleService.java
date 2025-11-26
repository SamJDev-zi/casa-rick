package com.casarick.api.service.imp;

import com.casarick.api.dto.SaleDTO;
import com.casarick.api.model.Sale;

import java.util.List;

public interface ISaleService {
    List<SaleDTO> getSales();
    SaleDTO getSaleById(Long id);
    SaleDTO createSale(SaleDTO saleDTO);
    SaleDTO updateSale(Long id, SaleDTO saleDTO);
}
