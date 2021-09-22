package gr.hellasdirect.houseinsurance;

import java.math.BigDecimal;

/**
 * Based on a {@link HouseDetails house}, this class calculates the premium amount to insure it.
 */
public interface HouseInsuranceService {
    /**
     * Calculate the premium to insure a house.
     *
     * @param house the house details
     * @return an instance of {@link ServiceResponse}. The premium will be found in ServiceResponse.getData()
     *  if the house can be insured.
     *  Otherwise, check getCode() and getMessage() for more details.
     */
    ServiceResponse<BigDecimal> getCalculatedPremium(HouseDetails house);
}
