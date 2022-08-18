package com.davidholcombe.discounts.domain.request;

import com.davidholcombe.discounts.domain.QuantityDTO;
import com.google.common.collect.ImmutableList;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBestDiscountRequest {

    @NonNull
    @Builder.Default
    private final List<QuantityDTO> items = ImmutableList.of();

}
