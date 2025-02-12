package com.ijse.gdse.carwash.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerTM {
    private String customerId;
    private String name;
    private String email;
    private String phone;
    private String userId;
}