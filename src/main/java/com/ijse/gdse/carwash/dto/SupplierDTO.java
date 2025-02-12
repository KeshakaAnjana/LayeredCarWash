package com.ijse.gdse.carwash.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class SupplierDTO {

    private String SupplierId;
    private String SupplierName;
    private String Status;
    private String ContactNo;
    private String UnitPrice;
    private String Qty;
}
