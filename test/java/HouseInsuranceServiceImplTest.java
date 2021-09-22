import gr.hellasdirect.houseinsurance.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.co.floodwatch.FloodRisk;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * These tests are based on the Acceptance Criteria from the document.
 */
public class HouseInsuranceServiceImplTest {

    private HouseDetails house;

    @BeforeEach
    void setUp() {
        this.house = new HouseDetails();
    }

    @Test
    @DisplayName("Should not insure house: invalid post code (1)")
    void testInvalidPostcode() {
        this.house.setPostCode("INVALID");
        HouseInsuranceService service = Mocks.getHouseInsuranceService(FloodRisk.LOW_RISK, false);
        ServiceResponse<BigDecimal> response = service.getCalculatedPremium(this.house);
        Assertions.assertEquals(ServiceCode.POST_CODE_NOT_FOUND, response.getCode());
    }

    @Test
    @DisplayName("Should not insure house: invalid post code (2)")
    void testInvalidPostcode2() {
        this.house.setPostCode("NOT_FOUND");
        HouseInsuranceService service = Mocks.getHouseInsuranceService(FloodRisk.LOW_RISK, false);
        ServiceResponse<BigDecimal> response = service.getCalculatedPremium(this.house);
        Assertions.assertEquals(ServiceCode.POST_CODE_NOT_FOUND, response.getCode());
    }

    @Test
    @DisplayName("Should not insure house: thatched roof")
    void testThatchedRoof() {
        this.house.setPostCode("VALID");
        this.house.setThatchedRoof(true);
        HouseInsuranceService service = Mocks.getHouseInsuranceService(FloodRisk.LOW_RISK, false);
        ServiceResponse<BigDecimal> response = service.getCalculatedPremium(this.house);
        Assertions.assertEquals(ServiceCode.THATCHED_ROOF, response.getCode());
    }

    @Test
    @DisplayName("Should not insure house: subsidence area")
    void testSubsidenceArea() {
        this.house.setPostCode("VALID");
        this.house.setThatchedRoof(false);
        HouseInsuranceService service = Mocks.getHouseInsuranceService(FloodRisk.LOW_RISK, true);
        ServiceResponse<BigDecimal> response = service.getCalculatedPremium(this.house);
        Assertions.assertEquals(ServiceCode.SUBSIDENCE_AREA, response.getCode());
    }

    @Test
    @DisplayName("Should not insure house: subsidence area")
    void testHighFloodRisk() {
        this.house.setPostCode("VALID");
        this.house.setThatchedRoof(false);
        HouseInsuranceService service = Mocks.getHouseInsuranceService(FloodRisk.HIGH_RISK, true);
        ServiceResponse<BigDecimal> response = service.getCalculatedPremium(this.house);
        Assertions.assertEquals(ServiceCode.FLOOD_RISK, response.getCode());
    }

    @Test
    @DisplayName("Should insure house: number of rooms 0, no thatched roof, no flood risk, not in subsidence area")
    void testInsuranceSuccess() {
        this.house.setNumBedrooms(0);
        this.house.setPostCode("VALID");
        this.house.setThatchedRoof(false);
        HouseInsuranceService service = Mocks.getHouseInsuranceService(FloodRisk.LOW_RISK, false);
        ServiceResponse<BigDecimal> response = service.getCalculatedPremium(this.house);
        Assertions.assertEquals(ServiceCode.OK, response.getCode());
        Assertions.assertEquals("110.00", new DecimalFormat("#.00").format(response.getData()));
    }

    @Test
    @DisplayName("Should insure house: number of rooms 2, no thatched roof, medium flood risk, not in subsidence area")
    void testInsuranceSuccessMediumFloodRisk() {
        this.house.setNumBedrooms(2);
        this.house.setPostCode("VALID");
        this.house.setThatchedRoof(false);
        HouseInsuranceService service = Mocks.getHouseInsuranceService(FloodRisk.MEDIUM_RISK, false);
        ServiceResponse<BigDecimal> response = service.getCalculatedPremium(this.house);
        Assertions.assertEquals(ServiceCode.OK, response.getCode());
        Assertions.assertEquals("126.50", new DecimalFormat("#.00").format(response.getData()));
    }
}
