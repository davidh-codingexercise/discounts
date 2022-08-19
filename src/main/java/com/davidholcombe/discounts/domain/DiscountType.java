package com.davidholcombe.discounts.domain;

import com.davidholcombe.discounts.repository.DiscountEntity;

public enum DiscountType  {

    FOR_ITEM_TYPE {
        @Override
        public Discount transformFromEntity(final DiscountEntity entity) {
            return DiscountForItemType.from(entity);
        }
    },
    FOR_COST {
        @Override
        public Discount transformFromEntity(final DiscountEntity entity) {
            return DiscountForItemCost.from(entity);
        }
    },
    FOR_COUNT {
        @Override
        public Discount transformFromEntity(final DiscountEntity entity) {
            return DiscountForItemCount.from(entity);
        }
    };

    public abstract Discount transformFromEntity(final DiscountEntity entity);
}
