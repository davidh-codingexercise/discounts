package com.davidholcombe.discounts.domain;

import com.davidholcombe.discounts.repository.DiscountEntity;
import com.google.common.collect.ImmutableMap;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Builder
public class DiscountForItemType implements Discount {

    @NonNull
    private String code;

    @NonNull
    private DiscountType type;

    @NonNull
    private BigDecimal percentageDiscount;

    @NonNull
    private ItemType itemType;

    @Override
    public BigDecimal calculateDiscountedTotalCost(final Map<Item, Long> items) {

        final BigDecimal totalCostBeforeDiscount = items.entrySet().stream()
                .map(entry -> entry.getKey().getCost().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        final ImmutableMap<Item, Long> itemsValidForDiscount = items.entrySet().stream()
                .filter(entry -> entry.getKey().getType() == itemType)
                .collect(ImmutableMap.toImmutableMap(Map.Entry::getKey, Map.Entry::getValue));

        if (itemsValidForDiscount.isEmpty()) {
            return totalCostBeforeDiscount;
        }

        final BigDecimal costOfEligibleItemsBeforeDiscount = itemsValidForDiscount.entrySet().stream()
                .map(entry -> entry.getKey().getCost().multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        final BigDecimal discount = costOfEligibleItemsBeforeDiscount.multiply(percentageDiscount);

        return totalCostBeforeDiscount.subtract(discount);
    }

    public static Discount from(final DiscountEntity entity) {

        return DiscountForItemType.builder()
                .code(entity.getCode())
                .type(entity.getType())
                .percentageDiscount(entity.getPercentageDiscount())
                .itemType(entity.getItemType().orElseThrow())
                .build();
    }
}
