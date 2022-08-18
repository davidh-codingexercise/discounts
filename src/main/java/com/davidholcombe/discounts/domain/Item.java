package com.davidholcombe.discounts.domain;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class Item {

    private long id;

    @NonNull
    private BigDecimal cost;

    @NonNull
    private ItemType type;

    @NonNull
    private String description;

}
