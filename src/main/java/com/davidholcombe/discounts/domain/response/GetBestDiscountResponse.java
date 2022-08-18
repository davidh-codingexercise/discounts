package com.davidholcombe.discounts.domain.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBestDiscountResponse {

    private String discountCode;

    private BigDecimal totalCost;

}
