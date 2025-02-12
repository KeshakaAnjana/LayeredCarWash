package com.ijse.gdse.carwash.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class BatchDTO {
    private String batchID;
    private String date;
    private String qty;
    private String price;
    private String productId;
}
