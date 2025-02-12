package com.ijse.gdse.carwash.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Payment1 {
    private String CustomerPaymentId;
    private String Amount;
    private String PaymentMethod;
    private String BookingId;

}
