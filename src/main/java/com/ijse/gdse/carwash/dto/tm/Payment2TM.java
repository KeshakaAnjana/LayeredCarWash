package com.ijse.gdse.carwash.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString


public class Payment2TM {
    private String SupplierPaymentId;
    private String Date;
    private String SupplierId;
    private String Qty;
    private String UnitPrice;
    private String Amount;

}
