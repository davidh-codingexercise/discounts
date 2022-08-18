package com.davidholcombe.discounts.domain.command;

import com.davidholcombe.discounts.domain.QuantityDTO;
import com.google.common.collect.ImmutableList;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBestDiscountCommand {

    @NonNull
    @Builder.Default
    private final List<QuantityDTO> items = ImmutableList.of();

}
