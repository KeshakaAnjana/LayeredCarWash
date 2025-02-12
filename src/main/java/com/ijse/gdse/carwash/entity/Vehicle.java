package com.ijse.gdse.carwash.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Vehicle {
    private String VehicleId;
    private String LicencePlate;
    private String Model;
    private String CustomerId;
}
