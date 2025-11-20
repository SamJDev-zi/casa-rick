package com.casarick.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sale")
@Builder
public class Sale {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "sale_id")
    private Long id;

    @Column(name = "sale_description")
    private String description;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "sale_amount")
    private BigDecimal amount;

    @Column(name = "sale_discount")
    private BigDecimal discount;

    @Column(name = "sale_total")
    private BigDecimal total;

    @Column(name = "created_at")
    private LocalDateTime created;

    @Column(name = "updated_at")
    private LocalDateTime updated;

    @ManyToOne
    @JoinColumn(
            name = "customer_id",
            referencedColumnName = "customer_id"
    )
    private Customer customer;

    @ManyToOne
    @JoinColumn(
            name = "employee_id",
            referencedColumnName = "employee_id"
    )
    private Employee employee;

    @ManyToOne
    @JoinColumn(
            name = "branch_id",
            referencedColumnName = "branch_id"
    )
    private Branch branch;

    @ManyToMany
    @JoinTable(
            name = "sale_detail",
            joinColumns = @JoinColumn(
                    name = "sale_id",
                    referencedColumnName = "sale_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "inventory_id",
                    referencedColumnName = "inventory_id"
            )
    )
    private List<Inventory> inventoryList;
}
