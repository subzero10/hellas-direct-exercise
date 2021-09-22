package gr.hellasdirect.houseinsurance;

import java.math.BigDecimal;

public class BaseAnnualPremiumChargeService {

    private static final BigDecimal BASE_ANNUAL_PREMIUM = BigDecimal.valueOf(110.00);

    public BigDecimal get() {
        return BASE_ANNUAL_PREMIUM;
    }
}
