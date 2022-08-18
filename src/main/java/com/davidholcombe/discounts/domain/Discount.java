package com.davidholcombe.discounts.domain;

import java.math.BigDecimal;
import java.util.Map;

public interface Discount {

    String getCode();

    BigDecimal calculateDiscountedTotalCost(final Map<Item, Long> items);

}
