package com.ijse.gdse.carwash.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Payment2 {
    private String SupplierPaymentId;
    private String Date;
    private String SupplierId;
    private String Qty;
    private String UnitPrice;
}
