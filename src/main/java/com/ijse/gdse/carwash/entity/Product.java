package com.ijse.gdse.carwash.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Product {
    private String productId;
    private String productName;
    private String date;
    private String price;
    private String qty;
}
