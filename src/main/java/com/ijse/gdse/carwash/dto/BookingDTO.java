package com.ijse.gdse.carwash.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class BookingDTO {
    private String BookingId;
    private String date;
    private String time;
    private String customerId;
    private String ServiceId;
}
