package com.ijse.gdse.carwash.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Booking {
    private String BookingId;
    private String date;
    private String time;
    private String customerId;
    private String ServiceId;
}
