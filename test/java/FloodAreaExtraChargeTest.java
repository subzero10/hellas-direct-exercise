import gr.hellasdirect.houseinsurance.HouseDetails;
import gr.hellasdirect.houseinsurance.chargecalculators.FloodAreaExtraChargeCalculator;
import gr.hellasdirect.houseinsurance.chargecalculators.HighFloodRiskException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.co.floodwatch.FloodRisk;
import uk.co.floodwatch.PostcodeNotFoundException;

public class FloodAreaExtraChargeTest {

    private HouseDetails house;
    private FloodAreaExtraChargeCalculator calculator;

    @BeforeEach
    void setUp() {
        this.house = new HouseDetails();
    }

    @Test
    @DisplayName("Should not have extra charge for no risk of flood")
    void testNoRiskFloodArea() {
        this.house.setPostCode("VALID");
        this.calculator = Mocks.getFloodAreaExtraChargeCalculator(FloodRisk.NO_RISK);
        double charge = this.calculator.calculateCharge(house);
        Assertions.assertEquals(0, charge);
    }

    @Test
    @DisplayName("Should not have extra charge for low risk flood area")
    void testLowRiskFloodArea() {
        this.house.setPostCode("VALID");
        this.calculator = Mocks.getFloodAreaExtraChargeCalculator(FloodRisk.LOW_RISK);
        double charge = this.calculator.calculateCharge(house);
        Assertions.assertEquals(0, charge);
    }

    @Test
    @DisplayName("Should have extra charge for medium risk flood area")
    void testMediumRiskFloodArea() {
        this.house.setPostCode("VALID");
        this.calculator = Mocks.getFloodAreaExtraChargeCalculator(FloodRisk.MEDIUM_RISK);
        double charge = this.calculator.calculateCharge(house);
        Assertions.assertEquals(0.15, charge);
    }

    @Test
    @DisplayName("Should throw for high risk flood area")
    void testHighRiskFloodArea() {
        this.house.setPostCode("VALID");
        this.calculator = Mocks.getFloodAreaExtraChargeCalculator(FloodRisk.HIGH_RISK);
        Assertions.assertThrows(HighFloodRiskException.class, () -> this.calculator.calculateCharge(house));
    }

    @Test
    @DisplayName("Should throw for invalid post code")
    void testInvalidPostCode() {
        this.house.setPostCode("INVALID");
        this.calculator = Mocks.getFloodAreaExtraChargeCalculator(FloodRisk.NO_RISK);
        Assertions.assertThrows(PostcodeNotFoundException.class, () -> this.calculator.calculateCharge(house));
    }
}
