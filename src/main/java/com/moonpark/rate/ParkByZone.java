package com.moonpark.rate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;

@Component
public class ParkByZone {

    final int MINUTES_IN_HOUR = 60;
    final int SECOND_IN_MINUTE = 60;
    final int MILLISECONDS_IN_SECOND = 1000;

    final int EIGHT_HUNDRED_HOURS = 8;
    final int SIXTEEN_HUNDRED_HOURS = 16;
    @Autowired
    TaxZoneConfig conf;
    @Autowired
    TaxZone taxZone;
    @Autowired
    private HashMap<String,ParkedVehicle> vehicles;


    public ParkByZone() {
        //TaxZone.valueOf(taxZoneArg).compareTo(TaxZone.M2.conf.getHourlyPrizeByZone(taxZoneArg));
        vehicles.put(conf.getRegNr(),new ParkedVehicle(LocalDateTime.now(ZoneId.of("UTC+2")),
                                                        conf.getNameM1(),
                                                        conf.getRegNr()));
    }

    public ParkByZone(String taxZone, String regNr) {
        //TaxZone.valueOf(taxZoneArg).compareTo(TaxZone.M2.conf.getHourlyPrizeByZone(taxZoneArg));
        vehicles.put(conf.getRegNr(),new ParkedVehicle(LocalDateTime.now(ZoneId.of("UTC+2")),
                                                        TaxZone.valueOf(taxZone).name(),
                                                        regNr));
    }

    public ParkedVehicle endParking(String regNr){
        ParkedVehicle vehicle = vehicles.get(regNr);
        long endParkingTime = vehicle.endParkingNowTimeStamp();
        double amountToPay =  calculateZoneTax(vehicle,
                                                findAmountOfMinutes(vehicle.getStartOfParking(),
                                                    endParkingTime),
                                taxZone.name());
        vehicle.setAmountToPay(amountToPay);
        return vehicle;
    }

    private double calculateZoneTax(ParkedVehicle vehicle, double amountOfMinutes, String taxZone){
        double amountOfHours = amountOfMinutes / MINUTES_IN_HOUR;
        return evaluatePrizeByZone(vehicle, taxZone, amountOfHours);
    }

    private double evaluatePrizeByZone(ParkedVehicle vehicle, String taxZone, double amountOfHours){
        if (TaxZone.valueOf(taxZone) == TaxZone.M2) {
            if(vehicle.getTimeStamp().getDayOfWeek().getValue() == DayOfWeek.SATURDAY.getValue() ||
                    vehicle.getTimeStamp().getDayOfWeek().getValue() == DayOfWeek.SUNDAY.getValue()){
                return amountOfHours * conf.getWeekendPriceM2();
            } else {
                return amountOfHours * conf.getHourlyPriceM2();
            }
        } else if (TaxZone.valueOf(taxZone) == TaxZone.M3) {
            if(vehicle.getTimeStamp().getDayOfWeek().getValue() == DayOfWeek.SUNDAY.getValue()){
                return amountOfHours * conf.getSundaysPriceM3();
            } else if(vehicle.getTimeStamp().getHour() > EIGHT_HUNDRED_HOURS &&
                    vehicle.getTimeStamp().getHour() < SIXTEEN_HUNDRED_HOURS) {
                return ((amountOfHours - conf.getHouresFreeM3()) * MINUTES_IN_HOUR) * conf.getHouresFreePriceM3();
             } else {
                return ((amountOfHours - conf.getHouresFreeM3()) * MINUTES_IN_HOUR) * conf.getHourlyPriceM3();
            }
        } else {
            return amountOfHours * conf.getHourlyPriceM1();
        }
    }

    private int findAmountOfMinutes(long start, long end){
        int minutes = (int) Math.floor(end - start / MILLISECONDS_IN_SECOND / SECOND_IN_MINUTE);
        if(minutes < 1) {
            return 0;
        } else {
            return minutes;
        }
    }

}
