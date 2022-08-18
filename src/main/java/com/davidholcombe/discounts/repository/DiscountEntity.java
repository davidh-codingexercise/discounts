package com.davidholcombe.discounts.repository;

import com.davidholcombe.discounts.domain.DiscountType;
import com.davidholcombe.discounts.domain.ItemType;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Optional;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "discounts")
public class DiscountEntity {

    @NonNull
    @Id
    @Column(name = "code")
    private String code;

    @NonNull
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private DiscountType type;

    @NonNull
    @Column(name = "percentage_discount")
    private BigDecimal percentageDiscount;

    @Column(name = "for_item_type")
    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    @Column(name = "for_cost")
    private BigDecimal cost;

    @Column(name = "for_quantity")
    private Long quantity;

    @Column(name = "for_quantity_item_id")
    private Long itemId;

    public Optional<ItemType> getItemType() {
        return Optional.ofNullable(itemType);
    }

    public Optional<BigDecimal> getCost() {
        return Optional.ofNullable(cost);
    }

    public Optional<Long> getQuantity() {
        return Optional.ofNullable(quantity);
    }

    public Optional<Long> getItemId() {
        return Optional.ofNullable(itemId);
    }
}
