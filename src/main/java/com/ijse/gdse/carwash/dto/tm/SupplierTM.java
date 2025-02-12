package com.ijse.gdse.carwash.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class SupplierTM {

    private String SupplierId;
    private String SupplierName;
    private String Status;
    private String ContactNo;
    private String UnitPrice;
    private String Qty;
}
