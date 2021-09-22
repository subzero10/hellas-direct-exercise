package gr.hellasdirect.houseinsurance.chargecalculators;

import gr.hellasdirect.houseinsurance.HouseDetails;
import uk.co.floodwatch.PostcodeNotFoundException;
import uk.co.subsidencewatch.SubsidenceAreaChecker;
import uk.co.subsidencewatch.SubsidenceAreaCheckerTechnicalFailureException;

@SuppressWarnings("ClassCanBeRecord")
public class SubsidenceAreaExtraChargeCalculator implements PremiumExtraChargeCalculator {

    private final SubsidenceAreaChecker checker;

    public SubsidenceAreaExtraChargeCalculator(SubsidenceAreaChecker checker) {
        this.checker = checker;
    }

    @Override
    public double calculateCharge(HouseDetails house) throws SubsidenceAreaCheckerTechnicalFailureException {
        if (this.checker == null) {
            throw new SubsidenceAreaCheckerTechnicalFailureException();
        }

        Boolean check = this.checker.isPostcodeInSubsidenceArea(house.getPostCode());
        if (check == null) {
            throw new PostcodeNotFoundException();
        }

        if (check) {
            throw new SubsidenceAreaException();
        }

        return 0;
    }
}
