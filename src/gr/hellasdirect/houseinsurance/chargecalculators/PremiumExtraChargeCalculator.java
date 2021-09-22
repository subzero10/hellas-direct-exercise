package gr.hellasdirect.houseinsurance.chargecalculators;

import gr.hellasdirect.houseinsurance.HouseDetails;
import uk.co.floodwatch.PostcodeNotFoundException;
import uk.co.subsidencewatch.SubsidenceAreaCheckerTechnicalFailureException;

/**
 * Implement this interface to add a calculator that would modify the base premium to insure a house.
 */
public interface PremiumExtraChargeCalculator {

    /**
     * Calculate the impact to the premium based on the house and return a percentage.
     *
     * @param house the house details
     * @return a percentage in decimal, i.e. 50% => 0.50
     * @throws Exception a number of exceptions such as {@link SubsidenceAreaCheckerTechnicalFailureException} or {@link PostcodeNotFoundException}
     */
    double calculateCharge(HouseDetails house) throws Exception;
}
