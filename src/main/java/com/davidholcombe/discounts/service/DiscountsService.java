package com.davidholcombe.discounts.service;

import com.davidholcombe.discounts.domain.Discount;
import com.davidholcombe.discounts.domain.DiscountFactory;
import com.davidholcombe.discounts.domain.Item;
import com.davidholcombe.discounts.domain.QuantityDTO;
import com.davidholcombe.discounts.domain.command.AddDiscountCommand;
import com.davidholcombe.discounts.domain.command.GetBestDiscountCommand;
import com.davidholcombe.discounts.domain.command.RemoveDiscountCommand;
import com.davidholcombe.discounts.domain.response.DiscountResponse;
import com.davidholcombe.discounts.domain.response.GetBestDiscountResponse;
import com.davidholcombe.discounts.domain.response.GetDiscountsResponse;
import com.davidholcombe.discounts.repository.DiscountEntity;
import com.davidholcombe.discounts.repository.DiscountRepository;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DiscountsService {

    private ItemsService itemsService;

    private DiscountRepository discountRepository;

    @Transactional
    public DiscountResponse addDiscount(final AddDiscountCommand command) {

        // TODO validation

        final DiscountEntity discountEntity = DiscountEntity.builder()
                .code(command.getCode())
                .type(command.getType())
                .percentageDiscount(command.getPercentageDiscount())
                .itemType(command.getAppliedForItemType().orElse(null))
                .cost(command.getAppliedForItemCost().orElse(null))
                .quantity(command.getAppliedForItemAndQuantity()
                        .map(QuantityDTO::getQuantity).orElse(null))
                .itemId(command.getAppliedForItemAndQuantity()
                        .map(QuantityDTO::getItemId).orElse(null))
                .build();

        final DiscountEntity savedDiscount = discountRepository.save(discountEntity);
        return DiscountResponse.from(savedDiscount);
    }

    @Transactional
    public void removeDiscount(final RemoveDiscountCommand command) {

        final Optional<DiscountEntity> discount = discountRepository.findById(command.getCode());

        // TODO change exception type
        discount.ifPresentOrElse(discountRepository::delete,
                () -> { throw new RuntimeException(String.format("Discount not found for code %s", command.getCode())); });
    }

    public GetDiscountsResponse getDiscounts() {

        final ImmutableList<DiscountResponse> discounts = discountRepository.findAll()
                .stream().map(DiscountResponse::from)
                .collect(ImmutableList.toImmutableList());

        return GetDiscountsResponse.builder().discounts(discounts).build();
    }

    public GetBestDiscountResponse getBestDiscount(final GetBestDiscountCommand command) {

        validateNoRepeatedItems(command);

        final ImmutableMap<Item, Long> items = command.getItems().stream()
                .collect(ImmutableMap.toImmutableMap(item -> itemsService.getItem(item.getItemId()), QuantityDTO::getQuantity));

        final ImmutableSet<Discount> discounts = discountRepository.findAll().stream()
                .map(DiscountFactory::from)
                .collect(ImmutableSet.toImmutableSet());

        final ImmutableMap<BigDecimal, String> discountedPricesByCode = discounts.stream()
                .collect(ImmutableMap.toImmutableMap(discount ->
                        discount.calculateDiscountedTotalCost(items), Discount::getCode));

        return discountedPricesByCode.entrySet().stream()
                .min(Map.Entry.comparingByKey())
                .map(entry -> GetBestDiscountResponse.builder()
                        .discountCode(entry.getValue())
                        .totalCost(entry.getKey()).build())
                .orElse(GetBestDiscountResponse.builder().build());
    }

    private void validateNoRepeatedItems(final GetBestDiscountCommand command) {

        // TODO change exception type
        // throw new RuntimeException("Request contains repeated items");
    }

}
