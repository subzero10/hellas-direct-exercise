package gr.hellasdirect.houseinsurance.chargecalculators;

import gr.hellasdirect.houseinsurance.HouseDetails;

public class BedroomExtraChargeCalculator implements PremiumExtraChargeCalculator {

    @SuppressWarnings("FieldCanBeLocal")
    private final int MAX_NUMBER_OF_BEDROOMS = 4;

    @Override
    public double calculateCharge(HouseDetails house) {
        if (house.getNumBedrooms() > MAX_NUMBER_OF_BEDROOMS) {
            throw new TooManyRoomsException();
        }

        return 0;
    }
}
