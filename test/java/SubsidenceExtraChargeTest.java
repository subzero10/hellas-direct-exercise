import gr.hellasdirect.houseinsurance.HouseDetails;
import gr.hellasdirect.houseinsurance.chargecalculators.SubsidenceAreaException;
import gr.hellasdirect.houseinsurance.chargecalculators.SubsidenceAreaExtraChargeCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.co.floodwatch.PostcodeNotFoundException;
import uk.co.subsidencewatch.SubsidenceAreaCheckerTechnicalFailureException;

public class SubsidenceExtraChargeTest {

    private HouseDetails house;
    private SubsidenceAreaExtraChargeCalculator calculator;

    @BeforeEach
    void setUp() {
        this.house = new HouseDetails();
    }

    @Test
    @DisplayName("Should not have extra charge if house is not in subsidence area")
    void testNotInSubsidenceArea() {
        this.house.setPostCode("VALID");
        this.calculator = Mocks.getSubsidenceExtraChargeCalculator(false);
        double charge = 0;
        try {
            charge = this.calculator.calculateCharge(house);
        } catch (SubsidenceAreaCheckerTechnicalFailureException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(0, charge);
    }

    @Test
    @DisplayName("Should throw if house is in subsidence area")
    void testInSubsidenceArea() {
        this.house.setPostCode("VALID");
        this.calculator = Mocks.getSubsidenceExtraChargeCalculator(true);
        Assertions.assertThrows(SubsidenceAreaException.class, () -> this.calculator.calculateCharge(house));
    }

    @Test
    @DisplayName("Should throw if post code is invalid")
    void testInvalidPostCode() {
        this.house.setPostCode("NOT_FOUND");
        this.calculator = Mocks.getSubsidenceExtraChargeCalculator(false);
        Assertions.assertThrows(PostcodeNotFoundException.class, () -> this.calculator.calculateCharge(house));
    }
}
