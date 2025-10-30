package com.product.api.ecommerce.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private String product_name;
    private String description;
    private BigDecimal price;
    private Byte categoryId;
}
