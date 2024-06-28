package com.moonpark.rest;

import com.moonpark.rate.ParkByZone;
import com.moonpark.rate.ParkedVehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestPark {

    @Autowired
    private ParkByZone park;

    @RequestMapping(value = "/takst",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ParkedVehicle startParking(@RequestParam String taxZone,
                                          @RequestParam String regNr) {
        park = new ParkByZone(taxZone, regNr);
        return park.endParking(regNr);
    }

}
