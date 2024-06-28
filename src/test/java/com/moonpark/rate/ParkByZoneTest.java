package com.moonpark.rate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = MoonParkApplication.class)
public class ParkByZoneTest {

    @Test
    public void testM1ZoneTax() {

        ParkByZone m1ZoneTax = new ParkByZone();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        double amountToPay = m1ZoneTax.endParking();

        assertTrue(amountToPay < 1 );

    }

    @Test
    public void testM2ZoneTax() {

        ParkByZone m2ZoneTax = new ParkByZone("M2");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        double amountToPay = m2ZoneTax.endParking();

        assertTrue(amountToPay < 1 );

    }

}
