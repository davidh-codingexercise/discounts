package com.davidholcombe.discounts.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class DiscountForItemCostTest {

    @Test
    void calculateDiscountedTotalCost_success() {

        // given
        final DiscountForItemCost discount = DiscountForItemCost.builder()
                .code("CDE")
                .type(DiscountType.FOR_COST)
                .percentageDiscount(BigDecimal.valueOf(0.15))
                .minimumCost(BigDecimal.valueOf(100))
                .build();

        final Item shirt = Item.builder()
                .id(123)
                .cost(BigDecimal.valueOf(50))
                .type(ItemType.CLOTHES)
                .description("shirt")
                .build();

        final Item tv = Item.builder()
                .id(456)
                .cost(BigDecimal.valueOf(300))
                .type(ItemType.ELECTRONICS)
                .description("tv")
                .build();

        final Map<Item, Long> items = Map.of(shirt, 1L, tv, 1L);

        // when
        final BigDecimal actual = discount.calculateDiscountedTotalCost(items);

        // then
        assertThat(actual.compareTo(BigDecimal.valueOf(305))).isZero();

    }

}