package com.davidholcombe.discounts.controller;

import com.davidholcombe.discounts.domain.command.AddDiscountCommand;
import com.davidholcombe.discounts.domain.command.GetBestDiscountCommand;
import com.davidholcombe.discounts.domain.command.RemoveDiscountCommand;
import com.davidholcombe.discounts.domain.request.AddDiscountRequest;
import com.davidholcombe.discounts.domain.request.GetBestDiscountRequest;
import com.davidholcombe.discounts.domain.response.DiscountResponse;
import com.davidholcombe.discounts.domain.response.GetBestDiscountResponse;
import com.davidholcombe.discounts.domain.response.GetDiscountsResponse;
import com.davidholcombe.discounts.service.DiscountsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/discounts")
public class DiscountsController {

    private DiscountsService discountsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DiscountResponse addDiscount(@RequestBody final AddDiscountRequest request) {

        final AddDiscountCommand command = AddDiscountCommand.builder()
                .code(request.getCode())
                .type(request.getType())
                .percentageDiscount(request.getPercentageDiscount())
                .appliedForItemType(request.getAppliedForItemType().orElse(null))
                .appliedForItemCost(request.getAppliedForItemCost().orElse(null))
                .appliedForItemAndQuantity(request.getAppliedForItemAndQuantity().orElse(null))
                .build();

        return discountsService.addDiscount(command);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public GetDiscountsResponse getDiscounts() {

        return discountsService.getDiscounts();
    }

    @DeleteMapping("/{code}")
    @ResponseStatus(HttpStatus.OK)
    public void removeDiscount(@PathVariable final String code) {

        final RemoveDiscountCommand command = RemoveDiscountCommand.builder().code(code).build();

        discountsService.removeDiscount(command);
    }

    @PostMapping("/best")
    @ResponseStatus(HttpStatus.OK)
    public GetBestDiscountResponse getBestDiscount(@RequestBody final GetBestDiscountRequest request) {

        final GetBestDiscountCommand command = GetBestDiscountCommand.builder()
                .items(request.getItems())
                .build();

        return discountsService.getBestDiscount(command);
    }

}
