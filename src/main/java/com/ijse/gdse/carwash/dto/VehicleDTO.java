package com.ijse.gdse.carwash.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class VehicleDTO {
    private String VehicleId;
    private String LicencePlate;
    private String Model;
    private String CustomerId;
}
