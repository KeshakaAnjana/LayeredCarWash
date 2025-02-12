package com.ijse.gdse.carwash.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class BatchTM {
    private String batchId;
    private String date;
    private String qty;
    private String price;
    private String productId;
}
