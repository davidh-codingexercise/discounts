package com.davidholcombe.discounts.domain;

import lombok.NonNull;
import lombok.Value;

@Value
public class Discount {

    @NonNull
    private String code;

    private double percentOff;

}
