package com.ijse.gdse.carwash.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class ProductDTO {
    private String productId;
    private String productName;
    private String date;
    private String price;
    private String qty;
}
