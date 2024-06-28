package com.moonpark.rate;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Getter
@SpringBootConfiguration
public class TaxZoneConfig {

    @Value("${moonpark.taxzone.m1.price}")
    private int hourlyPriceM1;

    @Value("${moonpark.taxzone.m1.name}")
    private String nameM1;

    @Value("${moonpark.taxzone.m2.price}")
    private int hourlyPriceM2;

    @Value("${moonpark.taxzone.m2.weekendprice}")
    private int weekendPriceM2;

    @Value("${moonpark.taxzone.m3.price}")
    private int hourlyPriceM3;

    @Value("${moonpark.taxzone.m3.pricesundays}")
    private int sundaysPriceM3;

    @Value("${moonpark.dummyuser.regnr}")
    private String regNr;

    @Value("${moonpark.taxzone.m3.houresfree}")
    private int houresFreeM3;

    @Value("${moonpark.taxzone.m3.firsthourfreeprice}")
    private int houresFreePriceM3;

    Integer getHourlyPrizeByZone(String zone) {
        if (zone.equals("M1")) {
            return hourlyPriceM1;
        } else if (zone.equals("M2")) {
            return hourlyPriceM2;
        } else if (zone.equals("M3")) {
            return hourlyPriceM3;
        } else {
            throw new IllegalArgumentException("Wrong zone!");
        }
    }

}
