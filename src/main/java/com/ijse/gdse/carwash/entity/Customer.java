package com.ijse.gdse.carwash.entity;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Customer {
    private String customerId;
    private String name;
    private String email;
    private String phone;
    private String userId;
}
