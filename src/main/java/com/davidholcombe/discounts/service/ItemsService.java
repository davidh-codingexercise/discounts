package com.davidholcombe.discounts.service;

import com.davidholcombe.discounts.domain.Item;
import com.davidholcombe.discounts.domain.ItemType;
import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ItemsService {

    // a basic, hardcoded implementation of a service to retrieve items

    private static final Item SHIRT = Item.builder()
            .id(123)
            .cost(BigDecimal.valueOf(50))
            .type(ItemType.CLOTHES)
            .description("shirt")
            .build();

    private static final Item TV = Item.builder()
            .id(456)
            .cost(BigDecimal.valueOf(300))
            .type(ItemType.ELECTRONICS)
            .description("tv")
            .build();

    private static final ImmutableMap<Long, Item> ALL_ITEMS =
            ImmutableMap.of(SHIRT.getId(), SHIRT, TV.getId(), TV);

    public Item getItem(final long id) {

        final Item item = ALL_ITEMS.get(id);

        // TODO replace exception type
        return Optional.ofNullable(item).orElseThrow(
                () -> new RuntimeException(String.format("Item with id %s not found", id)));
    }

}
