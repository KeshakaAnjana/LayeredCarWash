package com.ijse.gdse.carwash.dto.tm;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class BookingTM {
    private String BookingId;
    private String date;
    private String time;
    private String customerId;
    private String ServiceId;

}
