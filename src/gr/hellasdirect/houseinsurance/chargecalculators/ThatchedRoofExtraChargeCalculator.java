package gr.hellasdirect.houseinsurance.chargecalculators;

import gr.hellasdirect.houseinsurance.HouseDetails;

public class ThatchedRoofExtraChargeCalculator implements PremiumExtraChargeCalculator {

    @Override
    public double calculateCharge(HouseDetails house) {
        if (house.hasThatchedRoof()) {
            throw new ThatchedRoofException();
        }

        return 0;
    }
}
