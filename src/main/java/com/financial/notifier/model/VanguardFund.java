package com.financial.notifier.model;

import lombok.Getter;

@Getter
public enum VanguardFund {

    FTSE_GLOBAL("vanguard-ftse-global-all-cap-index-fund-gbp-acc"),
    SP_500("vanguard-s-and-p-500-ucits-etf-usd-accumulating");

    private final String qualifiedName;

    VanguardFund(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }
}
