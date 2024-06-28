package com.moonpark.rate;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@Component
public class ParkedVehicle {

    TaxZone taxZone;
    final String regNr;
    final LocalDateTime timeStamp;
    long endParkingTimestamp;
    double amountToPay;

    @Autowired
    ParkedVehicle(LocalDateTime time, String taxZone, String regnr) {
        this.timeStamp = time;
        this.taxZone = TaxZone.valueOf(taxZone);
        this.regNr = regnr;
    }

    public long endParkingNowTimeStamp() {
         endParkingTimestamp = System.currentTimeMillis();
         return endParkingTimestamp;
    }

    public long getStartOfParking() {
        return timeStamp.atZone(ZoneId.systemDefault()) // timezone
                .toInstant() // Instant object
                .toEpochMilli();
    }

}
