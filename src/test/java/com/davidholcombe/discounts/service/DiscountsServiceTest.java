package com.davidholcombe.discounts.service;

import com.davidholcombe.discounts.domain.DiscountType;
import com.davidholcombe.discounts.domain.Item;
import com.davidholcombe.discounts.domain.ItemType;
import com.davidholcombe.discounts.domain.QuantityDTO;
import com.davidholcombe.discounts.domain.command.GetBestDiscountCommand;
import com.davidholcombe.discounts.domain.response.GetBestDiscountResponse;
import com.davidholcombe.discounts.repository.DiscountEntity;
import com.davidholcombe.discounts.repository.DiscountRepository;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class DiscountsServiceTest {

    @Mock
    private ItemsService itemsService;

    @Mock
    private DiscountRepository repository;

    @InjectMocks
    private DiscountsService service;

    @BeforeEach
    private void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getBestDiscount_scenarioA_success() {

        // given
        // Discount ABC exists that gives 10% off all items of type CLOTHES
        // Discount CDE exists that gives 15% off all items over $100
        final DiscountEntity discountABC = DiscountEntity.builder()
                .code("ABC")
                .type(DiscountType.FOR_ITEM_TYPE)
                .percentageDiscount(BigDecimal.valueOf(0.10))
                .itemType(ItemType.CLOTHES)
                .build();

        final DiscountEntity discountCDE = DiscountEntity.builder()
                .code("CDE")
                .type(DiscountType.FOR_COST)
                .percentageDiscount(BigDecimal.valueOf(0.15))
                .cost(BigDecimal.valueOf(100))
                .build();

        Mockito.doReturn(ImmutableList.of(discountABC, discountCDE))
                .when(repository).findAll();

        final Item shirt = Item.builder()
                .id(123)
                .cost(BigDecimal.valueOf(50))
                .type(ItemType.CLOTHES)
                .description("shirt")
                .build();

        Mockito.doReturn(shirt)
                .when(itemsService).getItem(shirt.getId());

        final GetBestDiscountCommand command = GetBestDiscountCommand.builder()
                .items(ImmutableList.of(QuantityDTO.from(1, shirt.getId())))
                .build();

        // when
        // User submits a request to calculate the best discount for a $50 shirt
        // (id: 123, type: CLOTHES, cost: $50)
        final GetBestDiscountResponse actual = service.getBestDiscount(command);

        // then
        // The system should response with discount ABC and a total cost of $45
        assertThat(actual.getDiscountCode()).isEqualTo("ABC");
        assertThat(actual.getTotalCost().compareTo(BigDecimal.valueOf(45))).isZero();
    }

    @Test
    void getBestDiscount_scenarioB_success() {

        // given
        // Discount ABC exists that gives 10% off all items of type CLOTHES
        // Discount CDE exists that gives 15% off all items over $100
        // Discount FGH exists that gives 20% off when purchasing 2 or more of shirts with id 123
        final DiscountEntity discountABC = DiscountEntity.builder()
                .code("ABC")
                .type(DiscountType.FOR_ITEM_TYPE)
                .percentageDiscount(BigDecimal.valueOf(0.10))
                .itemType(ItemType.CLOTHES)
                .build();

        final DiscountEntity discountCDE = DiscountEntity.builder()
                .code("CDE")
                .type(DiscountType.FOR_COST)
                .percentageDiscount(BigDecimal.valueOf(0.15))
                .cost(BigDecimal.valueOf(100))
                .build();

        final DiscountEntity discountFGH = DiscountEntity.builder()
                .code("FGH")
                .type(DiscountType.FOR_COUNT)
                .percentageDiscount(BigDecimal.valueOf(0.20))
                .quantity(2L)
                .itemId(123L)
                .build();

        Mockito.doReturn(ImmutableList.of(discountABC, discountCDE, discountFGH))
                .when(repository).findAll();

        final Item shirt = Item.builder()
                .id(123)
                .cost(BigDecimal.valueOf(50))
                .type(ItemType.CLOTHES)
                .description("shirt")
                .build();

        Mockito.doReturn(shirt).when(itemsService).getItem(shirt.getId());

        final GetBestDiscountCommand command = GetBestDiscountCommand.builder()
                .items(ImmutableList.of(QuantityDTO.from(5, shirt.getId())))
                .build();

        // when
        // User submits a request to calculate the best discount for five $50 shirts
        // (id: 123, type: CLOTHES, cost: $50)
        final GetBestDiscountResponse actual = service.getBestDiscount(command);

        // then
        // The system should response with discount FGH and a total cost of $200
        assertThat(actual.getDiscountCode()).isEqualTo("FGH");
        assertThat(actual.getTotalCost().compareTo(BigDecimal.valueOf(200))).isZero();

    }

    @Test
    void getBestDiscount_scenarioC_success() {

        // given
        // Discount ABC exists that gives 10% off all items of type CLOTHES
        // Discount CDE exists that gives 15% off all items over $100
        final DiscountEntity discountABC = DiscountEntity.builder()
                .code("ABC")
                .type(DiscountType.FOR_ITEM_TYPE)
                .percentageDiscount(BigDecimal.valueOf(0.10))
                .itemType(ItemType.CLOTHES)
                .build();

        final DiscountEntity discountCDE = DiscountEntity.builder()
                .code("CDE")
                .type(DiscountType.FOR_COST)
                .percentageDiscount(BigDecimal.valueOf(0.15))
                .cost(BigDecimal.valueOf(100))
                .build();

        Mockito.doReturn(ImmutableList.of(discountABC, discountCDE))
                .when(repository).findAll();

        final Item shirt = Item.builder()
                .id(123)
                .cost(BigDecimal.valueOf(50))
                .type(ItemType.CLOTHES)
                .description("shirt")
                .build();

        final Item tv = Item.builder()
                .id(456)
                .cost(BigDecimal.valueOf(300))
                .type(ItemType.ELECTRONICS)
                .description("tv")
                .build();

        Mockito.doReturn(shirt).when(itemsService).getItem(shirt.getId());
        Mockito.doReturn(tv).when(itemsService).getItem(tv.getId());

        final GetBestDiscountCommand command = GetBestDiscountCommand.builder()
                .items(ImmutableList.of(
                        QuantityDTO.from(1, shirt.getId()),
                        QuantityDTO.from(1, tv.getId())))
                .build();

        // when
        // User submits a request to calculate the best discount for
        // one $50 shirt(id: 123, type: CLOTHES, cost: $50)
        // one $300 TV(id: 456, type: ELECTRONICS, cost: $300)
        final GetBestDiscountResponse actual = service.getBestDiscount(command);

        // then
        // The system should respond with discount CDE and a total cost of $305
        assertThat(actual.getDiscountCode()).isEqualTo("CDE");
        assertThat(actual.getTotalCost().compareTo(BigDecimal.valueOf(305))).isZero();
    }

}