package com.davidholcombe.discounts.controller;

import com.davidholcombe.discounts.domain.Item;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class DiscountsController {

    @PostMapping
    // TODO add return type for the discount, or header to location?
    public void addDiscount() {

    }

    @PostMapping
    public void removeDiscount() {

    }

    // TODO this is a GET because it doesn't change state,
    // but is it okay to provide items via querystring...?
    @GetMapping
    public void getBestDiscount(final Set<Item> items) {

    }

}
