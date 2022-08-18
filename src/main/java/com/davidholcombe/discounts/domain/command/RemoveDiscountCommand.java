package com.davidholcombe.discounts.domain.command;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RemoveDiscountCommand {

    @NonNull
    @NotEmpty
    private String code;

}
