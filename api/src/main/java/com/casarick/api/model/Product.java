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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
@Builder
public class Product {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "product_id")
    private Long id;

    @Column(name = "product_name")
    private String name;

    @ManyToOne
    @JoinColumn(
            name = "product_variant_id",
            referencedColumnName = "product_variant_id"
    )
    private ProductVariant variant;

    @Column(name = "product_photo_url")
    private String photoURL;

    @Column(name = "product_bar_code_number")
    private String barCode;

    @ManyToOne
    @JoinColumn(
            name = "branch_id",
            referencedColumnName = "branch_id"
    )
    private Branch branch;
}
