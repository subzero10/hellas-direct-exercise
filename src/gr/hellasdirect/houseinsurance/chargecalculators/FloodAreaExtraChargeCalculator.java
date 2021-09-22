package gr.hellasdirect.houseinsurance.chargecalculators;

import gr.hellasdirect.houseinsurance.HouseDetails;
import uk.co.floodwatch.FloodAreaChecker;
import uk.co.floodwatch.FloodAreaCheckerTechnicalFailureException;
import uk.co.floodwatch.FloodRisk;

public class FloodAreaExtraChargeCalculator implements PremiumExtraChargeCalculator {

    @SuppressWarnings("FieldCanBeLocal")
    private final double MEDIUM_RISK_EXTRA_CHARGE_PERCENTAGE = 0.15;
    private final FloodAreaChecker checker;

    public FloodAreaExtraChargeCalculator(FloodAreaChecker checker) {
        this.checker = checker;
    }

    @Override
    public double calculateCharge(HouseDetails house) {
        if (this.checker == null) {
            throw new FloodAreaCheckerTechnicalFailureException();
        }

        FloodRisk risk = this.checker.isPostcodeInFloodArea(house.getPostCode());

        switch (risk) {
            case MEDIUM_RISK -> {
                return MEDIUM_RISK_EXTRA_CHARGE_PERCENTAGE;
            }
            case HIGH_RISK -> {
                throw new HighFloodRiskException();
            }
            default -> {
                return 0;
            }
        }
    }
}
