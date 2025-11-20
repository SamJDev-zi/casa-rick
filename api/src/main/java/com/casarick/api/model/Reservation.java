package com.casarick.api.model;

import jakarta.persistence.*;
import jakarta.persistence.JoinColumn;
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
@Table(name = "reservation")
@Builder
public class Reservation {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "reservation_id")
    private Long id;

    @Column(name = "reservation_description")
    private String description;

    @Column(name = "reservation_deposit_amount")
    private BigDecimal amount;

    @Column(name = "reservation_balance_due")
    private BigDecimal balance;

    @Column(name = "reservation_status")
    private String status;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "created_at")
    private LocalDateTime created;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

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
            name = "reservation_detail",
            joinColumns = @JoinColumn(
                    name = "reservation_id",
                    referencedColumnName = "reservation_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "inventory_id",
                    referencedColumnName = "inventory_id"
            )
    )
    private List<Inventory> inventoryList;
}
