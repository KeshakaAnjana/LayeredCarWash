package com.ijse.gdse.carwash.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Payment2DTO {
    private String SupplierPaymentId;
    private String Date;
    private String SupplierId;
    private String Qty;
    private String UnitPrice;
}
