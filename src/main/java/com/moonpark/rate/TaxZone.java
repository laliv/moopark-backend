package com.moonpark.rate;

import org.springframework.beans.factory.annotation.Value;

public enum TaxZone {
    M1(60), //Per hour
    M2(100), //Per hour
    M3(2); // Per minute

    private int unityPrice;

    TaxZone(int unityPrice) {
        this.unityPrice = unityPrice;
    }

    public int getNumber() {
        return unityPrice;
    }

}
