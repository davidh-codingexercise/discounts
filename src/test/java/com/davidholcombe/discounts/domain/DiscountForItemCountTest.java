package com.davidholcombe.discounts.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class DiscountForItemCountTest {

    @Test
    void calculateDiscountedTotalCost_success() {

        // given
        final DiscountForItemCount discount = DiscountForItemCount.builder()
                .code("FGH")
                .type(DiscountType.FOR_COUNT)
                .percentageDiscount(BigDecimal.valueOf(0.20))
                .quantity(2)
                .itemId(123)
                .build();

        final Item shirt = Item.builder()
                .id(123)
                .cost(BigDecimal.valueOf(50))
                .type(ItemType.CLOTHES)
                .description("shirt")
                .build();

        final Map<Item, Long> items = Map.of(shirt, 5L);

        // when
        final BigDecimal actual = discount.calculateDiscountedTotalCost(items);

        // then
        assertThat(actual.compareTo(BigDecimal.valueOf(200))).isZero();

    }

    @Test
    void calculateDiscountedTotalCost_notEnoughItems_success() {

        // given
        final DiscountForItemCount discount = DiscountForItemCount.builder()
                .code("FGH")
                .type(DiscountType.FOR_COUNT)
                .percentageDiscount(BigDecimal.valueOf(0.20))
                .quantity(11)
                .itemId(123)
                .build();

        final Item shirt = Item.builder()
                .id(123)
                .cost(BigDecimal.valueOf(50))
                .type(ItemType.CLOTHES)
                .description("shirt")
                .build();

        final Map<Item, Long> items = Map.of(shirt, 10L);

        // when
        final BigDecimal actual = discount.calculateDiscountedTotalCost(items);

        // then
        assertThat(actual.compareTo(BigDecimal.valueOf(500))).isZero();

    }

    @Test
    void calculateDiscountedTotalCost_exactNumberOfItems_success() {

        // given
        final DiscountForItemCount discount = DiscountForItemCount.builder()
                .code("FGH")
                .type(DiscountType.FOR_COUNT)
                .percentageDiscount(BigDecimal.valueOf(0.20))
                .quantity(2)
                .itemId(123)
                .build();

        final Item shirt = Item.builder()
                .id(123)
                .cost(BigDecimal.valueOf(50))
                .type(ItemType.CLOTHES)
                .description("shirt")
                .build();

        final Map<Item, Long> items = Map.of(shirt, 2L);

        // when
        final BigDecimal actual = discount.calculateDiscountedTotalCost(items);

        // then
        assertThat(actual.compareTo(BigDecimal.valueOf(80))).isZero();

    }

}