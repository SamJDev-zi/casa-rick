package com.casarick.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inventory")
@Builder
public class Inventory {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "inventory_id")
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "product_id",
            referencedColumnName = "product_id"
    )
    private Product product;

    @Column(name = "inventory_stock")
    private int stock;

    @Column(name = "product_cost_price")
    private BigDecimal costPrice;

    @Column(name = "product_sale_price")
    private BigDecimal salePrice;

    @Column(name = "created_at")
    private LocalDateTime created;

    @Column(name = "updated_at")
    private LocalDateTime updated;

    @ManyToOne
    @JoinColumn(
            name = "branch_id",
            referencedColumnName = "branch_id"
    )
    private Branch branch;
}
