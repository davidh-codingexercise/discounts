package com.davidholcombe.discounts.domain.command;

import com.davidholcombe.discounts.domain.DiscountType;
import com.davidholcombe.discounts.domain.ItemType;
import com.davidholcombe.discounts.domain.QuantityDTO;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Optional;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddDiscountCommand {

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

    public Optional<ItemType> getAppliedForItemType() {
        return Optional.ofNullable(appliedForItemType);
    }

    public Optional<BigDecimal> getAppliedForItemCost() {
        return Optional.ofNullable(appliedForItemCost);
    }

    public Optional<QuantityDTO> getAppliedForItemAndQuantity() {
        return Optional.ofNullable(appliedForItemAndQuantity);
    }

}
