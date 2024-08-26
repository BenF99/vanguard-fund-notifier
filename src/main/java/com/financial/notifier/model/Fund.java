package com.financial.notifier.model;

import lombok.Data;

@Data
public class Fund {
    private String name;
    private NavPrice navPrice;

    @Data
    public static class NavPrice {
        private String amountChange;
        private String asOfDate;
        private String percentChange;
        private String value;
        private String mmAmountChange;
        private String mmValue;
    }
}