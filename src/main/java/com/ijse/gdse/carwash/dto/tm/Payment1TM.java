package com.ijse.gdse.carwash.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Payment1TM {
    private String CustomerPaymentId;
    private String Amount;
    private String PaymentMethod;
    private String BookingId;
}
