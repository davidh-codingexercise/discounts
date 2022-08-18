package com.davidholcombe.discounts.domain.response;

import com.davidholcombe.discounts.domain.DiscountType;
import com.davidholcombe.discounts.domain.ItemType;
import com.davidholcombe.discounts.domain.QuantityDTO;
import com.davidholcombe.discounts.repository.DiscountEntity;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountResponse {

    @NonNull
    @NotEmpty
    private String code;

    @NonNull
    private DiscountType type;

    @NonNull
    private BigDecimal percentageDiscount;

    private ItemType appliedForItemType;

    private BigDecimal appliedForItemCost;

    private QuantityDTO appliedForItemAndQuantity;

    public static DiscountResponse from(final DiscountEntity entity) {

        final DiscountResponseBuilder builder = DiscountResponse.builder()
                .code(entity.getCode())
                .type(entity.getType())
                .percentageDiscount(entity.getPercentageDiscount())
                .appliedForItemType(entity.getItemType().orElse(null))
                .appliedForItemCost(entity.getCost().orElse(null));

        if (entity.getQuantity().isPresent() && entity.getItemId().isPresent()) {
            builder.appliedForItemAndQuantity(QuantityDTO.builder()
                    .itemId(entity.getItemId().orElseThrow())
                    .quantity(entity.getQuantity().orElseThrow())
                    .build());
        }

        return builder.build();
    }
}
