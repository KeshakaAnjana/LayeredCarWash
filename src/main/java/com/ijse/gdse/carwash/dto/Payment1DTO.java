package com.ijse.gdse.carwash.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Payment1DTO {
    private String CustomerPaymentId;
    private String Amount;
    private String PaymentMethod;
    private String BookingId;

}
