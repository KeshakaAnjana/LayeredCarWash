package com.ijse.gdse.carwash.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Supplier {

    private String SupplierId;
    private String SupplierName;
    private String Status;
    private String ContactNo;
    private String UnitPrice;
    private String Qty;
}
