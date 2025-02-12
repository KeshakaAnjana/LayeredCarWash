package com.ijse.gdse.carwash.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerDTO {
    private String customerId;
    private String name;
    private String email;
    private String phone;
    private String userId;
}
