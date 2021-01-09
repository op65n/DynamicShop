package com.sebbaindustries.dynamicshop.engine.components;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SItemPricing {

    private Double priceBuy = 0.00D;

    private Double priceSell = 0.00D;

    private Integer buys = 0;

    private Integer sells = 0;

    private Boolean flatline = true;

}
