package com.davidholcombe.discounts.domain;

import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
public class Item {

    @NonNull
    private long id;

    @NonNull
    private BigDecimal cost;

    @NonNull
    private ItemType type;

}
