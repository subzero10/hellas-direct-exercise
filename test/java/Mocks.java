import gr.hellasdirect.houseinsurance.BaseAnnualPremiumChargeService;
import gr.hellasdirect.houseinsurance.HouseInsuranceService;
import gr.hellasdirect.houseinsurance.HouseInsuranceServiceImpl;
import gr.hellasdirect.houseinsurance.chargecalculators.*;
import uk.co.floodwatch.FloodRisk;
import uk.co.floodwatch.PostcodeNotFoundException;

public final class Mocks {

    public static SubsidenceAreaExtraChargeCalculator getSubsidenceExtraChargeCalculator(boolean isInSubsidenceArea) {
        return new SubsidenceAreaExtraChargeCalculator(postcode -> {
            if (postcode.equals("NOT_FOUND")) {
                return null;
            }
            return isInSubsidenceArea;
        });
    }

    public static FloodAreaExtraChargeCalculator getFloodAreaExtraChargeCalculator(FloodRisk risk) {
        return new FloodAreaExtraChargeCalculator(postcode -> {
            if (postcode.equals("INVALID")) {
                throw new PostcodeNotFoundException();
            }
            return risk;
        });
    }

    public static HouseInsuranceService getHouseInsuranceService(FloodRisk floodRisk, boolean isInSubsidenceArea) {
        BaseAnnualPremiumChargeService baseAnnualPremiumChargeService = new BaseAnnualPremiumChargeService();
        ExtraChargesCalculatorRegistry registry = new ExtraChargesCalculatorRegistry();
        registry.registerCalculators(new PremiumExtraChargeCalculator[]{
                new BedroomExtraChargeCalculator(),
                new ThatchedRoofExtraChargeCalculator(),
                Mocks.getFloodAreaExtraChargeCalculator(floodRisk),
                Mocks.getSubsidenceExtraChargeCalculator(isInSubsidenceArea)
        });
        return new HouseInsuranceServiceImpl(baseAnnualPremiumChargeService, registry);
    }
}
