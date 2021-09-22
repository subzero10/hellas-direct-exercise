import gr.hellasdirect.houseinsurance.HouseDetails;
import gr.hellasdirect.houseinsurance.chargecalculators.BedroomExtraChargeCalculator;
import gr.hellasdirect.houseinsurance.chargecalculators.TooManyRoomsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BedroomExtraChargeTest {

    private HouseDetails house;
    private BedroomExtraChargeCalculator calculator;

    @BeforeEach
    void setUp() {
        this.calculator = new BedroomExtraChargeCalculator();
        this.house = new HouseDetails();
    }

    @Test
    @DisplayName("Should not have extra charge for 0 bedrooms")
    void testNoBedroomsNoExtraCharge() {
        this.house.setNumBedrooms(0);
        double charge = this.calculator.calculateCharge(house);
        Assertions.assertEquals(0, charge);
    }

    @Test
    @DisplayName("Should not have extra charge for 4 bedrooms")
    void testFourBedroomsNoExtraCharge() {
        this.house.setNumBedrooms(4);
        double charge = this.calculator.calculateCharge(house);
        Assertions.assertEquals(0, charge);
    }

    @Test
    @DisplayName("Should throw if rooms are more than 4")
    void testFiveBedroomsNoInsurance() {
        this.house.setNumBedrooms(5);
        Assertions.assertThrows(TooManyRoomsException.class, () -> this.calculator.calculateCharge(house));
    }

}
