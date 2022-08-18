package com.davidholcombe.discounts.domain.response;

import com.google.common.collect.ImmutableList;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetDiscountsResponse {

    @NonNull
    @Builder.Default
    private final ImmutableList<DiscountResponse> discounts = ImmutableList.of();

}
