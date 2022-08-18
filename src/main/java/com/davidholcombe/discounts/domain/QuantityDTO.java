package com.davidholcombe.discounts.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuantityDTO {

    private long quantity;

    private long itemId;

    public static QuantityDTO from(final long quantity, final long itemId) {
        return QuantityDTO.builder()
                .quantity(quantity)
                .itemId(itemId)
                .build();
    }

}
