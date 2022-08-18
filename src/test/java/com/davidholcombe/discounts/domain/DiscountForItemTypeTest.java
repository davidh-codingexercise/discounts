package com.davidholcombe.discounts.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class DiscountForItemTypeTest {

    @Test
    void calculateDiscountedTotalCost_success() {

        // given
        final DiscountForItemType discount = DiscountForItemType.builder()
                .code("ABC")
                .type(DiscountType.FOR_ITEM_TYPE)
                .percentageDiscount(BigDecimal.valueOf(0.10))
                .itemType(ItemType.CLOTHES)
                .build();

        final Item shirt = Item.builder()
                .id(123)
                .cost(BigDecimal.valueOf(50))
                .type(ItemType.CLOTHES)
                .description("shirt")
                .build();

        final Map<Item, Long> items = Map.of(shirt, 1L);

        // when
        final BigDecimal actual = discount.calculateDiscountedTotalCost(items);

        // then
        assertThat(actual.compareTo(BigDecimal.valueOf(45))).isZero();

    }

}