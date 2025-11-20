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
@Table(name = "product_variant")
@Builder
public class ProductVariant {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(name = "product_variant_id")
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "category_id",
            referencedColumnName = "category_id"
    )
    private Category category;
    @ManyToOne
    @JoinColumn(
            name = "clothe_type_id",
            referencedColumnName = "clothe_type_id"
    )
    private ClotheType clotheType;

    @ManyToOne
    @JoinColumn(
            name = "industry_id",
            referencedColumnName = "industry_id"
    )
    private Industry industry;

    @Column(name = "product_color")
    private String color;

    @Column(name = "product_size")
    private String size;
}
