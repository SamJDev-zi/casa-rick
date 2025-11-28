package com.casarick.api.reposiroty;

import com.casarick.api.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    // 🌟 Solución Recomendada
    @Query("SELECT s FROM Sale s JOIN FETCH s.inventoryList")
    List<Sale> findAllWithInventory();
}
