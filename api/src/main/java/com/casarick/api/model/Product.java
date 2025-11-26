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

    @Column(name = "product_photo_url")
    private String photoURL;

    @Column(name = "product_bar_code_number")
    private String barCode;
}
