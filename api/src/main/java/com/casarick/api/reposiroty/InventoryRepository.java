package com.casarick.api.reposiroty;

import com.casarick.api.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    // Fórmula: Suma de ((PrecioVenta - PrecioCosto) * Stock)
    @Query("SELECT SUM((i.salePrice - i.costPrice) * i.stock) FROM Inventory i")
    BigDecimal getTotalApproximateProfit();
}
