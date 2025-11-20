package com.casarick.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "branch")
@Builder
public class Branch {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "branch_id")
    private Long id;

    @Column(name = "branch_name")
    private String name;

    @Column(name = "branch_address")
    private String address;

    @Column(name = "branch_phone")
    private String phoneNumber;

    @Column(name = "is_active")
    private boolean isActive;

    @ManyToOne
    @JoinColumn(
            name = "manager_id",
            referencedColumnName = "manager_id"
    )
    private Manager manager;
}
