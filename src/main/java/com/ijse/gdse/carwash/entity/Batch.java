package com.ijse.gdse.carwash.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Batch {
    private String batchID;
    private String date;
    private String qty;
    private String price;
    private String productId;
}
